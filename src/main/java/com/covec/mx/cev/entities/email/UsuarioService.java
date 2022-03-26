package com.covec.mx.cev.entities.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void updateResetPasswordToken(String token, String email) throws UsuarioNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            usuario.setResetPasswordToken(token);
            usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNotFoundException("No se ha podido encontrar ningún usuario con el correo electrónico " + email);
        }
    }
    //usuario pertenece a un correo dado
    public Usuario getByResetPasswordToken(String token) {
        return usuarioRepository.findByResetPasswordToken(token);
    }
    //actualizar la contraseña
    public void updatePassword(Usuario usuario, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        usuario.setPassword(encodedPassword);

        usuario.setResetPasswordToken(null);
        usuarioRepository.save(usuario);
    }
}
