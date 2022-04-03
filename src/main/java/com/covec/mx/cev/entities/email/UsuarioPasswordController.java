package com.covec.mx.cev.entities.email;

import com.covec.mx.cev.config.EmailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class UsuarioPasswordController {
    @Autowired
    private  UsuarioService usuarioService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/forgot_password")
    public String mostrarFormulario(Model model){
        return "email/forgot_password_form";
    }

   @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);
        try {
            usuarioService.updateResetPasswordToken(token,email);
            //envia gmail
            String restPasswordLink = Utility.getSiteURL(request)+ "/reset_password?token=" + token;
            //genera link
            emailService.sendEmail(email,restPasswordLink);
            model.addAttribute("message", "Hemos enviado un enlace para restablecer la contraseña a su correo electrónico. Por favor, compruebe.");
        } catch (UsuarioNotFoundException e) {
            model.addAttribute("error",e.getMessage());
        }catch (UnsupportedEncodingException |MessagingException e){
            model.addAttribute("error", "Error al enviar un correo electrónico");
        }

        //System.out.println("Email" + email);
        //System.out.println("Token" + token);
        return "email/forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (usuario == null) {
            model.addAttribute("message", "Token Invalido");
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
            model.addAttribute("message","Token Invalido");
            return "email/message";
        }else  {
            usuarioService.updatePassword(usuario,password);
            model.addAttribute("message","Has cambiado satisfactoriamente tu contraseña.");
        }
        return "email/message";
    }
}
