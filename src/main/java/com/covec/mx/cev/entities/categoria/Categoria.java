package com.covec.mx.cev.entities.categoria;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\\-\\/+=@_ ]*$", message = "No se permiten caracteres especiales en el nombre de la categoría")
    @NotEmpty(message = "El nombre no debe estar vacío")
    @Size(min = 5, max = 45,message = "El nombre de la categoría debe tener un mínimo de 5 y máximo de 45 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Incidencia> incidencias;

    public String toStringCategoria() {
        return "categoria: { nombre:" + nombre + "}";
    }

    
}
