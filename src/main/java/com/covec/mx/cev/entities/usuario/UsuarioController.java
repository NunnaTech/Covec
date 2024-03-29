package com.covec.mx.cev.entities.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.covec.mx.cev.config.EmailService;
import com.covec.mx.cev.entities.email.UsuarioNotFoundException;
import com.covec.mx.cev.entities.email.Utility;
import com.covec.mx.cev.entities.sesion.SesionService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private SesionService sesionService;

    @GetMapping("/")
    public String login() {
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(Usuario usuario, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        Usuario user = usuarioService.findByUsername(username);
        session.setAttribute("user", user);
        sesionService.guardarSesion(user.getId());
        return "index";
    }

    @GetMapping("/perfil")
    public String profileUser(HttpSession httpSession, Model model) {
        Usuario session = (Usuario) httpSession.getAttribute("user");
        model.addAttribute("usuario", session);
        return "perfil";
    }

    @PostMapping("/actualizar")
    public String save(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
            RedirectAttributes attributes) {
        Usuario existingUser = repository.findByUsername(usuario.getUsername());
        Usuario updateUser = repository.getById(usuario.getId());
        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        } else {
            if (existingUser != null) {
                if (existingUser.getId() == updateUser.getId()) {
                    usuarioService.update(usuario);
                    attributes.addFlashAttribute("mensaje", "Se realizo el cambio correctamente, cierre sesión para ver los cambios.");
                }else{
                    attributes.addFlashAttribute("errores", "El correo electronico ya esta en uso.");
                }
            } else {
                usuarioService.update(usuario);
                attributes.addFlashAttribute("mensaje", "Se realizo el cambio correctamente, cierre sesión para ver los cambios.");
            }
        }
        return "redirect:/perfil/";
    }

    @PostMapping("/updatePassword")
    public String uptadePassword(Model model, @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword, RedirectAttributes attributes) {
        Usuario usuario = usuarioService.getOne(idUsuario);
        if (this.passwordEncoder.matches(oldPassword, usuario.getPassword())) {
            usuario.setPassword(this.passwordEncoder.encode(newPassword));
            this.usuarioService.save(usuario);
            attributes.addFlashAttribute("mensaje", "Se cambio correctamente la contraseña");
        } else {
            attributes.addFlashAttribute("errores", "Verifique que sea su contraseña correcta");
        }
        return "redirect:/perfil/";
    }

    @GetMapping("/forgot_password")
    public String mostrarFormulario(Model model) {
        return "email/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model, RedirectAttributes attributes) {
        String email = request.getParameter("username");
        String token = RandomString.make(45);
        token += LocalDateTime.now();
        try {
            usuarioService.updateResetPasswordToken(token, email);
            String restPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            emailService.sendEmail(email, restPasswordLink);
            attributes.addFlashAttribute("mensaje",
                    "Hemos enviado un enlace para restablecer la contraseña a su correo electrónico. Por favor, compruebe.");
        } catch (UsuarioNotFoundException e) {
            attributes.addFlashAttribute("errores", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            attributes.addFlashAttribute("errores", "Correo electronico inválido");
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
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Usuario usuario = usuarioService.getByResetPasswordToken(token);
        model.addAttribute("title", "Restablecer su contraseña");
        if (usuario == null) {
            model.addAttribute("message", "Ya has cambiado tu contraseña");
            return "email/message";
        } else {
            usuarioService.updatePassword(usuario, password);
            model.addAttribute("message", "Has cambiado tu contraseña satisfactoriamente.");
        }
        return "email/message";
    }
}
