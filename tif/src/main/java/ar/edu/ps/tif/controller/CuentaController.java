package ar.edu.ps.tif.controller;

import ar.edu.ps.tif.dto.CuentaDTO;
import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.model.Cuenta;
import ar.edu.ps.tif.service.IClienteService;
import ar.edu.ps.tif.service.ICuentaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin
@PreAuthorize("denyAll()")
public class CuentaController {

    @Autowired
    private ICuentaService cuentaService;

    @Autowired
    private IClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('CREATE')")
    public ResponseEntity<Cuenta> guardar(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.guardar(cuenta));
    }
    // @PostMapping
    // @PreAuthorize("hasRole('CLIENTE')")
    // public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta,
    // @RequestParam Long clienteId) {
    // Cliente cliente = clienteService.obtenerPorId(clienteId);
    // if (cliente == null) {
    // return ResponseEntity.badRequest().build();
    // }

    // cuenta.setClientes(Set.of(cliente));
    // return ResponseEntity.ok(cuentaService.guardar(cuenta));
    // }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<List<Cuenta>> listarTodas() {
        return ResponseEntity.ok(cuentaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<Cuenta> obtenerPorId(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.obtenerPorId(id);
        return cuenta != null ? ResponseEntity.ok(cuenta) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO') or hasAuthority('UPDATE')")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta existente = cuentaService.obtenerPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();
        cuenta.setId(id);
        return ResponseEntity.ok(cuentaService.guardar(cuenta));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------
    @GetMapping("/mis-cuentas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Set<Cuenta>> obtenerCuentasDelClienteAutenticado(Authentication auth) {
        Cliente cliente = clienteService.findByUsername(auth.getName());
        if (cliente == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente.getCuentas());
    }

    @PostMapping("/mi-cuenta")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Cuenta> crearCuentaParaClienteAutenticado(Authentication auth,  @RequestBody CuentaDTO cuentaDto) {

    Cliente cliente = clienteService.findByUsername(auth.getName());
    if (cliente == null)
        return ResponseEntity.badRequest().build();

    Cuenta cuenta = new Cuenta();
    cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
    cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
    cuenta.setSaldo(cuentaDto.getSaldo());

    Cuenta nueva = cuentaService.guardar(cuenta);

    // Asociar cuenta al cliente
    cliente.getCuentas().add(nueva);
    clienteService.guardar(cliente);

    return ResponseEntity.ok(nueva);
    
    }
}
