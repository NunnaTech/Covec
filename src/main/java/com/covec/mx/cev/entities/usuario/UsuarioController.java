package com.covec.mx.cev.entities.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.covec.mx.cev.config.EmailService;
import com.covec.mx.cev.entities.email.UsuarioNotFoundException;
import com.covec.mx.cev.entities.email.Utility;


@Controller
public class UsuarioController {
    @Autowired
    private EmailService emailService;
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
		session.setAttribute("user", usuarioService.findByUsername(username));
        return "index";
    }



    @GetMapping("/forgot_password")
    public String mostrarFormulario(Model model){
        return "email/forgot_password_form";
    }

   @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("username");
        String token = RandomString.make(45);
        token+= LocalDateTime.now();
        try {
            usuarioService.updateResetPasswordToken(token,email);
            String restPasswordLink = Utility.getSiteURL(request)+ "/reset_password?token=" + token;
            emailService.sendEmail(email,restPasswordLink);
            model.addAttribute("message", "Hemos enviado un enlace para restablecer la contraseña a su correo electrónico. Por favor, compruebe.");
        } catch (UsuarioNotFoundException e) {
            model.addAttribute("error",e.getMessage());
        }catch (UnsupportedEncodingException | MessagingException e){
            model.addAttribute("error", "Error al enviar un correo electrónico");
        }
        return "redirect:/login";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (usuario == null) {
            model.addAttribute("message", "Ya has cambiado tu contraseña");
            return "email/message";
        }
        return "email/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model){
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("title","Restablecer su contraseña");
        if (usuario == null){
            model.addAttribute("message","Ya has cambiado tu contraseña");
            return "email/message";
        }else  {
            usuarioService.updatePassword(usuario,password);
            model.addAttribute("message","Has cambiado tu contraseña satisfactoriamente.");
        }
        return "email/message";
    }
}
