package ar.edu.ps.tif.service;

import ar.edu.ps.tif.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    List<Cliente> listarTodos();
    Cliente guardar(Cliente cliente);
    Cliente obtenerPorId(Long id);
    void eliminar(Long id);
    Optional<Cliente> findByNombre(String nombre);
    Cliente findByUsername(String username);
    void guardarCliente(Cliente cliente);
    
    
}
