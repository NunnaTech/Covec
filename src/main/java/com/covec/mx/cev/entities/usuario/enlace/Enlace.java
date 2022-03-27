package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.comentario.Comentario;
import com.covec.mx.cev.entities.municipio.Municipio;
import com.covec.mx.cev.entities.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Enlace extends Usuario {
    @Column(name = "num_empleado")
    private String numEmpleado;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @OneToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @OneToMany(mappedBy = "enlace")
    private List<Comentario> comentarios;

}
