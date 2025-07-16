package ar.edu.ps.tif.controller;

import ar.edu.ps.tif.dto.ClienteUpdateDTO;
import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.model.Cuenta;
import ar.edu.ps.tif.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
@PreAuthorize("denyAll()")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('CREATE')")
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO') or hasAuthority('UPDATE')")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente existente = clienteService.obtenerPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();
        cliente.setId(id);
        return ResponseEntity.ok(clienteService.guardar(cliente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---
    // @GetMapping("/by-username/{username}")
    // @PreAuthorize("hasRole('CLIENTE')")
    // public ResponseEntity<Cliente> getByUsername(@PathVariable String username) {
    //     return ResponseEntity.ok(clienteService.findByUsername(username));
    // }

    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Cliente> getByUsername(@PathVariable String username) {
    if (username == null || username.equalsIgnoreCase("null") || username.equalsIgnoreCase("undefined")) {
        return ResponseEntity.badRequest().build();
    }

    Cliente cliente = clienteService.findByUsername(username);
    if (cliente == null) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(cliente);
    }


    @GetMapping("/{id}/cuentas")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO', 'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<Set<Cuenta>> obtenerCuentasPorCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente.getCuentas());
    }

    // ----
    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Cliente> actualizarMisDatos(
            Authentication auth,
            @RequestBody ClienteUpdateDTO datos) {

        Cliente actual = clienteService.findByUsername(auth.getName());
        if (actual == null)
            return ResponseEntity.notFound().build();

        actual.setNombre(datos.getNombre());
        actual.setDni(datos.getDni());

        return ResponseEntity.ok(clienteService.guardar(actual));
    }

}
