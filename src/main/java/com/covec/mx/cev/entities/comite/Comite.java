package com.covec.mx.cev.entities.comite;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Comites")
public class Comite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComites")
    private Integer id;

    @ManyToMany
    @JoinColumn(name = "id_integrante")
    private Integrante integrante;

    @ManyToMany
    @JoinColumn(name = "id_colonia")
    private Colonia colonia;
}
