package com.covec.mx.cev.entities.sesion;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Sesiones")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesiones")
    private Integer id;
    @Column(name = "fecha_sesion")
    private Date fechaSesion;
}
