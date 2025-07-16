package ar.edu.ps.tif.repository;

import ar.edu.ps.tif.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findByNumeroCuenta(String numeroCuenta);
}
