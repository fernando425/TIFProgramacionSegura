package ar.edu.ps.tif.repository;


import ar.edu.ps.tif.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
}
