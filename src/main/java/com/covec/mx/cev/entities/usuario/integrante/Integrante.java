package com.covec.mx.cev.entities.usuario.integrante;

import com.covec.mx.cev.entities.comite.Comite;
import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.pago.Pago;
import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("Integrante")
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
