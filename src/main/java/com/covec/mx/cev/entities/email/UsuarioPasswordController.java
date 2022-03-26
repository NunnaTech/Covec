package com.covec.mx.cev.entities.email;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class UsuarioPasswordController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private  UsuarioService usuarioService;

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
            sendEmail(email,restPasswordLink);
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
            model.addAttribute("message", "Invalid Token");
            return "message";
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
    private void sendEmail(String recipientEmail, String restPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("soportecovec@gmail.com", "Covec Support");
        helper.setTo(recipientEmail);
        String subject = "Este es el enlace para restablecer la contraseña";

        String content = "<p>Hello,</p>"
                + "<p>Ha solicitado restablecer su contraseña.</p>"
                + "<p>Haga clic en el siguiente enlace para cambiar su contraseña:</p>"
                + "<p><a href=\"" + restPasswordLink + "\">Cambiar mi contraseña</a></p>"
                + "<br>"
                + "<p>Ignore este correo electrónico si recuerda su contraseña, "
                + "o no ha hecho la solicitud.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
