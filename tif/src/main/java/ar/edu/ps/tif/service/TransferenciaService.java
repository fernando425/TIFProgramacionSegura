package ar.edu.ps.tif.service;

import ar.edu.ps.tif.dto.TransferenciaDTO;
import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.model.Cuenta;
import ar.edu.ps.tif.model.Transferencia;
import ar.edu.ps.tif.repository.ICuentaRepository;
import ar.edu.ps.tif.repository.ITransferenciaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TransferenciaService implements ITransferenciaService {

    private final ITransferenciaRepository transferenciaRepository;

    @Autowired
    private ICuentaRepository cuentaRepository;

    public TransferenciaService(ITransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @Override
    public List<Transferencia> listarTodos() {
        return transferenciaRepository.findAll();
    }

    @Override
    public Transferencia guardar(Transferencia transferencia) {
        return transferenciaRepository.save(transferencia);
    }

    @Override
    public Transferencia obtenerPorId(Long id) {
        return transferenciaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        transferenciaRepository.deleteById(id);
    }

    public Transferencia guardar(TransferenciaDTO dto) {
        Cuenta origen = cuentaRepository.findByNumeroCuenta(dto.getOrigen());
        Cuenta destino = cuentaRepository.findByNumeroCuenta(dto.getDestino());

        if (origen == null || destino == null) {
            throw new RuntimeException("Cuenta origen o destino no encontrada");
        }

        Transferencia t = new Transferencia();
        t.setCuentaOrigen(origen);
        t.setCuentaDestino(destino);
        t.setMonto(dto.getMonto());
        t.setFecha(LocalDateTime.now());

        // Actualiza saldos
        origen.setSaldo(origen.getSaldo().subtract(dto.getMonto()));
        destino.setSaldo(destino.getSaldo().add(dto.getMonto()));

        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        return transferenciaRepository.save(t);
    }

    // ------------------------
    @Override
    public List<Transferencia> obtenerPorCliente(Cliente cliente) {
        Set<Cuenta> cuentas = cliente.getCuentas();
        List<Transferencia> resultado = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            resultado.addAll(transferenciaRepository.findByCuentaOrigenOrCuentaDestino(cuenta, cuenta));
        }

        return resultado;
    }
}
