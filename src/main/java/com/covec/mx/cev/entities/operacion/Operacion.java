package com.covec.mx.cev.entities.operacion;

import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Operaciones")
@Data
@NoArgsConstructor
public class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOperacion")
    private Integer id;
    @Column(name = "accion")
    private String accion;
    @Column(name = "anterior")
    private String anterior;
    @Column(name = "actual")
    private String actual;
    @Column(name = "fecha_hora")
    private Date fechaHora;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
}
