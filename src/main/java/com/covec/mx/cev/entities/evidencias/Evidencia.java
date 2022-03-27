package com.covec.mx.cev.entities.evidencias;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Evidencias")
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
    private Integer id;

    @Column(name = "evidencia")
    private String evidencia;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
}
