package com.covec.mx.cev.entities.usuario;

import com.covec.mx.cev.entities.operacion.Operacion;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<Operacion> operaciones;
}
