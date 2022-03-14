package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.Municipio;
import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Enlace extends Usuario {
    @Column(name = "num_empleado")
    private String numEmpleado;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @OneToMany(mappedBy = "enlace")
    private List<Municipio> municipios;
}
