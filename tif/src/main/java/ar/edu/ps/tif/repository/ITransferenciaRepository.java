package ar.edu.ps.tif.repository;

import ar.edu.ps.tif.model.Cuenta;
import ar.edu.ps.tif.model.Transferencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByCuentaOrigenOrCuentaDestino(Cuenta origen, Cuenta destino);
}
