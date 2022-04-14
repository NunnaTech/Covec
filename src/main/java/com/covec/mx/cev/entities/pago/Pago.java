package com.covec.mx.cev.entities.pago;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Pagos")
@Data
@NoArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagos")
    private Integer id;
    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "fecha_pago")
    private String fecha;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
}
