package com.covec.mx.cev.entities.usuario;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import com.covec.mx.cev.entities.email.UsuarioNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public void updateResetPasswordToken(String token, String email) throws UsuarioNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(email);
        if (usuario != null && checkTokenDate(token)) {
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

    public Boolean checkTokenDate(String token){
        try {
            LocalDateTime tokenDate = LocalDateTime.parse(token.substring(45, token.length()));
            long hours = ChronoUnit.HOURS.between(tokenDate, LocalDateTime.now());
            if(hours < 24) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
         return false;
     }
}
