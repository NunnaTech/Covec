package com.covec.mx.cev.entities.comite;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Comites")
public class Comite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comites")
    private Integer id;

    @OneToMany(mappedBy = "comite")
    private List<Integrante> integrantes;

    @ManyToOne
    @JoinColumn(name = "id_colonia")
    private Colonia colonia;
}
