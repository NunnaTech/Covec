package com.covec.mx.cev.entities.usuario.integrante;

import com.covec.mx.cev.entities.comite.Comite;
import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.pago.Pago;
import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Integrante extends Usuario {
    @Column(name = "es_presidente")
    private Boolean presidente;

    @OneToMany(mappedBy = "integrante")
    private List<Incidencia> incidencias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_comites")
    private Comite comite;

    @OneToMany(mappedBy = "integrante")
    private List<Pago> pagos;
}