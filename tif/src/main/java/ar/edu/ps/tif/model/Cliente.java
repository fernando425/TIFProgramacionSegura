package ar.edu.ps.tif.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String dni;

    @Column(unique = true)
    private String username;

    

    @ManyToMany
    @JoinTable(name = "cliente_cuenta", 
    joinColumns = @JoinColumn(name = "cliente_id"), 
    inverseJoinColumns = @JoinColumn(name = "cuenta_id"))
    @JsonManagedReference
    private Set<Cuenta> cuentas = new HashSet<>();
}