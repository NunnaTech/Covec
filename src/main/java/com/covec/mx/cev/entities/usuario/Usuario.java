package com.covec.mx.cev.entities.usuario;

import com.covec.mx.cev.entities.operacion.Operacion;
import com.covec.mx.cev.entities.rol.Rol;

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
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Column(name = "enabled")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario")
    private List<Operacion> operaciones;

    @ManyToMany
    @JoinTable
    (
        name = "authorities",
        joinColumns = @JoinColumn(name="id_usuario"),
        inverseJoinColumns = @JoinColumn(name="id_authority")
    )
    List<Rol> roles;
}
