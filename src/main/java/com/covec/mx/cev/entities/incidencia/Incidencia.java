package com.covec.mx.cev.entities.incidencia;

import com.covec.mx.cev.entities.categoria.Categoria;
import com.covec.mx.cev.entities.comentario.Comentario;
import com.covec.mx.cev.entities.evidencias.Evidencia;
import com.covec.mx.cev.entities.pago.Pago;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Incidencias")
@Data
@NoArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencias")
    private Integer id;
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ,.\\-\\/+=@_ ]*$", message = "No se permiten caracteres especiales en la descripción de la incidencia")
    @NotEmpty(message = "La descripción no debe de estar vacia")
    @Size(min = 20, max = 128,message = "La descripción debe tener un mínimo de 20 y máximo de 128 caracteres")
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "pagar")
    private Boolean pagar;
    @Column(name = "estatus")
    private String estatus;
    @Column(name = "monto")
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_integrante")
    private Integrante integrante;

    @OneToMany(mappedBy = "incidencia")
    private List<Evidencia> evidencias;

    @OneToMany(mappedBy = "incidencia")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "incidencia")
    private List<Pago> pagos;
}
