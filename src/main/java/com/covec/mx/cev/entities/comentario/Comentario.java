package com.covec.mx.cev.entities.comentario;

import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Comentario_Incidencia")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario_Incidencia")
    private Integer id;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_enlace")
    private Enlace enlace;

    @ManyToOne
    @JoinColumn(name = "id_integrante")
    private Integrante integrante;
}
