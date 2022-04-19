package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.ForgotPasswordServicio;
import com.egg.proyectospring.servicios.MailServicio;
import javax.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cambiar_email")
public class ForgotEmailContoller {

    @Autowired
    private ForgotPasswordServicio forgotPasswordServicio;

    @Autowired
    private MailServicio mailServicio;

    @GetMapping("")
    public String forgotPassword(Model model) {
        model.addAttribute("titulo", "Cambiar email");
        return "cambiar-email";
    }

    @PostMapping("/save")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(36);

        try {
            forgotPasswordServicio.setPasswordToken(token, email);

            String resetPasswordLink = forgotPasswordServicio.getSiteURL(request) + "/cambiar_email/form?token=" + token;

            mailServicio.cambiarEmail(email, resetPasswordLink);

            model.addAttribute("titulo", "Cambiar email");
            model.addAttribute("success", "Hemos enviado un enlace de cambio de email a su correo electrónico. Por favor, verifiquelo.");

            return "cambiar-email";
        } catch (Exception ex) {
            model.addAttribute("titulo", "Cambiar email");
            model.addAttribute("error", ex.getMessage());
            return "cambiar-email";
        }
    }

    @GetMapping("/form")
    public String mostrarResetPassword(@RequestParam("token") String token, Model model) {

        Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
        if (u == null) {
            model.addAttribute("titulo", "Actualizar tu contraseña");
            model.addAttribute("error", "Token invalido");
            return "reset-password-form";
        }

        model.addAttribute("titulo", "Actualizar tu contraseña");
        model.addAttribute("token", token);
        return "reset-email-form";
    }

    @PostMapping("/form")
    public String processResetPassword(HttpServletRequest request, Model model) {

        String token = request.getParameter("token");
        String email = request.getParameter("email");

        Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
        if (u == null) {
            model.addAttribute("titulo", "Actualizar tu email");
            model.addAttribute("error", "Token invalido");
            return "reset-email-form";
        } else {
            forgotPasswordServicio.actualizarEmail(u, email);
            model.addAttribute("titulo", "Actualizar tu email");
            model.addAttribute("success", "Has cambiado satisfactoriamente tu email");
        }

        return "reset-email-form";
    }

}
