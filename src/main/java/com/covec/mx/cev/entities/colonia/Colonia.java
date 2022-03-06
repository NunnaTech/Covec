package com.covec.mx.cev.entities.colonia;

import com.covec.mx.cev.entities.municipio.Municipio;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Colonias")
public class Colonia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idColonia")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codigo_postal")
    private String codigoPostal;

    @ManyToMany
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @OneToMany(mappedBy = "colonia")
    private List<Colonia> colonias;
}
