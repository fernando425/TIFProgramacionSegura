package ar.edu.ps.tif.repository;

import ar.edu.ps.tif.model.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
    
}
