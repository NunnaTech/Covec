package com.covec.mx.cev.entities.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UsuarioController {
        
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String login(){
        return"redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, HttpSession session){

        String username = authentication.getName();
		//Add data user session

		session.setAttribute("user", usuarioService.findByUsername(username));

        return "index";
    }
}
