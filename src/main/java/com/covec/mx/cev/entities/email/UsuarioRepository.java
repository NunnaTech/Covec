package com.covec.mx.cev.entities.email;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    public Usuario findByEmail(String email);

    public Usuario findByResetPasswordToken(String token);
}
