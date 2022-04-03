package com.covec.mx.cev.entities.email;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public  class Usuario {
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
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;


    // @OneToMany(mappedBy = "usuario")
   // private List<Operacion> operaciones;
}