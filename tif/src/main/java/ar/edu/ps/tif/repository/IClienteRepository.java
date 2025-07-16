package ar.edu.ps.tif.repository;

import ar.edu.ps.tif.model.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombre(String nombre);
     Optional<Cliente> findByUsername(String username);
}
