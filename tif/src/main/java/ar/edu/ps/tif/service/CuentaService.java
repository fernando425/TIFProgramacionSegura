package ar.edu.ps.tif.service;

import ar.edu.ps.tif.model.Cuenta;
import ar.edu.ps.tif.repository.ICuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService implements ICuentaService {

    private final ICuentaRepository cuentaRepository;

    public CuentaService(ICuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<Cuenta> listarTodos() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        if (cuenta.getNumeroCuenta() == null || cuenta.getNumeroCuenta().isBlank()) {
            cuenta.setNumeroCuenta(generarNumeroCuentaUnico());
        }
        return cuentaRepository.save(cuenta);
    }

    private String generarNumeroCuentaUnico() {
        String numero;
        do {
            numero = String.valueOf((int) (Math.random() * 1_000_000_000)); // Ejemplo básico
        } while (cuentaRepository.findByNumeroCuenta(numero) != null);
        return numero;
    }


    @Override
    public Cuenta obtenerPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        cuentaRepository.deleteById(id);
    }
}
