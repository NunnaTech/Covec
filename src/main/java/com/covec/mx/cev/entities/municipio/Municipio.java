package com.covec.mx.cev.entities.municipio;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Municipios")
@Data
@NoArgsConstructor
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "municipio")
    private List<Colonia> colonias;

    @OneToMany(mappedBy = "municipio")
    private List<Enlace> enlaces;
}
