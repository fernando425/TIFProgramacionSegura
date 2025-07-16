package ar.edu.ps.tif.service;

import ar.edu.ps.tif.model.Cuenta;
import java.util.List;

public interface ICuentaService {
    List<Cuenta> listarTodos();
    Cuenta guardar(Cuenta cuenta);
    Cuenta obtenerPorId(Long id);
    void eliminar(Long id);
}
