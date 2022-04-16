package com.covec.mx.cev.entities.usuario;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.transaction.Transactional;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.email.UsuarioNotFoundException;

import com.covec.mx.cev.entities.usuario.enlace.Enlace;
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
    public Usuario getOne(int id){
        Optional<Usuario> exist = usuarioRepository.findById(id);
        if (exist.isPresent()){
            Usuario obj;
            obj = exist.get();
            return obj;
        }
        return null;
    }
    public Usuario save(Usuario newObject){
        usuarioRepository.save(newObject);
        return newObject;
    }

    public Usuario update(Usuario newObject){
        Optional<Usuario> exist = Optional.empty();
        Usuario usuario = null;
        exist = usuarioRepository.findById(newObject.getId());
        if (!exist.isEmpty()){
            exist.get().setUsername(newObject.getUsername());
            exist.get().setNombreCompleto(newObject.getNombreCompleto());
            exist.get().setTelefono(newObject.getTelefono());
            usuarioRepository.save(exist.get());
            usuario = exist.get();
        }

        return usuario;
    }

    public void delete(Integer id){
        usuarioRepository.deleteById(id);
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

     public Usuario findByTelefono(String telefono){
        return usuarioRepository.findByTelefono(telefono);
     }

     public String deleteByTelefono(String telefono){
         return usuarioRepository.deleteByTelefono(telefono);
     }

}
