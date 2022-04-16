package com.covec.mx.cev.entities.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    
    Usuario findByUsername(String username);

    public Usuario findByResetPasswordToken(String token);

    public Usuario findByTelefono(String telefono);

    public String deleteByTelefono(String telefono); 
}
