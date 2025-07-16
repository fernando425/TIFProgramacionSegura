package ar.edu.ps.tif.controller;

import ar.edu.ps.tif.dto.TransferenciaDTO;
import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.model.Transferencia;
import ar.edu.ps.tif.service.IClienteService;
import ar.edu.ps.tif.service.ITransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@CrossOrigin
@PreAuthorize("denyAll()")
public class TransferenciaController {

    @Autowired
    private ITransferenciaService transferenciaService;

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<List<Transferencia>> listarTodas() {
        return ResponseEntity.ok(transferenciaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('READ')")
    public ResponseEntity<Transferencia> obtenerPorId(@PathVariable Long id) {
        Transferencia t = transferenciaService.obtenerPorId(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('CREATE')")
    public ResponseEntity<Transferencia> guardar(@RequestBody Transferencia t) {
        return ResponseEntity.ok(transferenciaService.guardar(t));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO') or hasAuthority('UPDATE')")
    public ResponseEntity<Transferencia> actualizar(@PathVariable Long id, @RequestBody Transferencia t) {
        Transferencia existente = transferenciaService.obtenerPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();
        t.setId(id);
        return ResponseEntity.ok(transferenciaService.guardar(t));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transferenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------
    @GetMapping("/mis-transferencias")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Transferencia>> obtenerMisTransferencias(Authentication auth) {
        Cliente cliente = clienteService.findByUsername(auth.getName());
        List<Transferencia> lista = transferenciaService.obtenerPorCliente(cliente);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/realizar")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') or hasAuthority('CREATE')")
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody TransferenciaDTO dto) {
        return ResponseEntity.ok(transferenciaService.guardar(dto));
    }

    // ----
    
    @GetMapping("/mis-movimientos")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Transferencia>> movimientos(Authentication auth) {
        Cliente cliente = clienteService.findByUsername(auth.getName());
        return ResponseEntity.ok(transferenciaService.obtenerPorCliente(cliente));
    }
}
