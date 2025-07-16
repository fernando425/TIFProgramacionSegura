package ar.edu.ps.tif.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuentas")
public class Cuenta {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    private BigDecimal saldo;

    @ManyToMany(mappedBy = "cuentas")
    @JsonBackReference 
    private Set<Cliente> clientes;

    @OneToMany(mappedBy = "cuentaOrigen")
    @JsonBackReference("origen")
    private List<Transferencia> transferenciasEmitidas;

    @OneToMany(mappedBy = "cuentaDestino")
    @JsonBackReference("destino")
    private List<Transferencia> transferenciasRecibidas;
}
