package com.covec.mx.cev.entities.rol;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.covec.mx.cev.entities.usuario.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor

public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private Integer id;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
    
}
