package ar.edu.ps.tif.service;

import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.repository.IClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<Cliente> findByNombre(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }

    // -----
    public Cliente findByUsername(String username) {
        return clienteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

}
