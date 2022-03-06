package com.covec.mx.cev.entities.incidencia;

import com.covec.mx.cev.entities.categoria.Categoria;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Incidencias")
@Data
@NoArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIncidencias")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "evidencia")
    private byte[] evidencia;
    @Column(name = "pagar")
    private Boolean pagar;
    @Column(name = "estatus")
    private String estatus;

    @ManyToMany()
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToMany()
    @JoinColumn(name = "id_integrante")
    private Integrante integrante;
}
