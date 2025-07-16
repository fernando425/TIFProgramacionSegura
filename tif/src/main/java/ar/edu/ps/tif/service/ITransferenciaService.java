package ar.edu.ps.tif.service;

import ar.edu.ps.tif.dto.TransferenciaDTO;
import ar.edu.ps.tif.model.Cliente;
import ar.edu.ps.tif.model.Transferencia;
import java.util.List;

public interface ITransferenciaService {
    List<Transferencia> listarTodos();
    Transferencia guardar(Transferencia transferencia);
    Transferencia obtenerPorId(Long id);
    void eliminar(Long id);
    Transferencia guardar(TransferenciaDTO dto);

    List<Transferencia> obtenerPorCliente(Cliente cliente);

}
