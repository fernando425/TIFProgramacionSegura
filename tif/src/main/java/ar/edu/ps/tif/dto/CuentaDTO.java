package ar.edu.ps.tif.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldo;
}
