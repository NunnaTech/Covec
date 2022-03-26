package com.covec.mx.cev.entities.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuario")
public class CuentaUsuarioController {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/")
    public String loginUsuario(Usuario usuario , Model model) {
        model.addAttribute("usuario",usuario);
        return "email/login";
    }

    @PostMapping("/login")
    public ModelAndView loggeo(Usuario usuario, ModelAndView modelAndView) {
        Usuario existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser != null) {
            // Use encoder.matches para comparar la contraseña sin formato con la contraseña cifrada

            if (encoder.matches(usuario.getPassword(), existingUser.getPassword())) {
                // Iniciado sesión con éxito
                modelAndView.addObject("menssage","Vientos");
                modelAndView.setViewName("email/successLogin");
            } else {
                // Contraseña incorrecta
                modelAndView.addObject("menssage","Incorrecto Pass");
                modelAndView.setViewName("email/login");
            }
        } else {
            modelAndView.addObject("menssage","Incorrecto Email");
            modelAndView.setViewName("email/login");
        }
        return modelAndView;
    }

}
