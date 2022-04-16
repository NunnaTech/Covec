package com.covec.mx.cev.entities.colonia;

import com.covec.mx.cev.entities.comite.Comite;
import com.covec.mx.cev.entities.municipio.Municipio;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Colonias")
public class Colonia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colonia")
    private Integer id;

    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\\-\\/+=@_ ]*$", message = "No se permiten caracteres especiales en el nombre de la colonia")
    @Size(min = 4, max = 45,message = "El nombre de la colonia debe tener un mínimo de 4 y máximo de 45 caracteres")
    @NotEmpty(message = "El nombre no debe estar vacío")
    @Column(name = "nombre")
    private String nombre;
    
    @Pattern(regexp = "^[0-9]*$", message = "Solo se permiten números en el C.P.")
    @Size(min = 5, max = 5,message = "El C.P de la colonia debe tener 5 dígitos")
    @NotEmpty(message = "El C.P. no debe estar vacío")
    @Column(name = "codigo_postal")
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @OneToMany(mappedBy = "colonia", cascade = {CascadeType.ALL})
    private List<Comite> comites;

    public String toStringColonia() {
        return "colonia: { codigoPostal:" + codigoPostal + ", nombre:" + nombre + "}";
    }

    
}
