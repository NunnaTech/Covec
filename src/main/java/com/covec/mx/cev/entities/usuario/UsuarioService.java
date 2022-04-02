package com.covec.mx.cev.entities.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
}
