package com.covec.mx.cev.entities.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    
    @GetMapping("/")
    public String login(){
        return"login";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "index";
    }
}
