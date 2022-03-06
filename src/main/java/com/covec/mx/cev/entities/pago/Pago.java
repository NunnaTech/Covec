package com.covec.mx.cev.entities.pago;

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
    @Column(name = "idPagos")
    private Integer id;
    @Column(name = "cantidad")
    private Double cantidad;

    @ManyToMany()
    @JoinColumn(name = "id_integrante")
    private Integrante integrante;
}
