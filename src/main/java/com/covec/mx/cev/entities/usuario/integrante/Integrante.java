package com.covec.mx.cev.entities.usuario.integrante;

import com.covec.mx.cev.entities.comite.Comite;
import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.pago.Pago;
import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idsuario")
public class Integrante extends Usuario {
    @Column(name = "esPresidente")
    private Boolean presidente;

    @OneToMany(mappedBy = "integrante")
    private List<Incidencia> incidencias;

    @OneToMany(mappedBy = "integrante")
    private List<Comite> comites;

    @OneToMany(mappedBy = "integrante")
    private List<Pago> pagos;
}
