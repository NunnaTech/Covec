package com.covec.mx.cev.entities.municipio;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\\-\\/+=@_ ]*$", message = "No se permiten caracteres especiales en el nombre del municipio")
    @NotEmpty(message = "El nombre no debe estar vacío")
    @Size(min = 4, max = 45,message = "El nombre del municipio debe tener un mínimo de 4 y máximo de 45 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "municipio")
    private List<Colonia> colonias;

    @OneToMany(mappedBy = "municipio")
    private List<Enlace> enlaces;
}
