package com.covec.mx.cev.entities.comentario;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Comentario_Incidencia")
public class Comentario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario_Incidencia")
    private Integer id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "es_enlace")
    private Boolean esEnlace;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "id_enlace")
    private Enlace enlace;
}
