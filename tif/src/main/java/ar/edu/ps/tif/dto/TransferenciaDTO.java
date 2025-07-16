package ar.edu.ps.tif.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaDTO {
    private String origen;
    private String destino;
    private BigDecimal monto;
}
