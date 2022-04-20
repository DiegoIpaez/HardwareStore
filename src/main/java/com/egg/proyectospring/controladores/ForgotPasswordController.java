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
@RequestMapping("/forgot_password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordServicio forgotPasswordServicio;

    @Autowired
    private MailServicio mailServicio;

    @GetMapping("")
    public String forgotPassword(Model model) {
        model.addAttribute("titulo", "Olvide la password");
        return "forgot-password-form";
    }

    @PostMapping("/save")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(36);

        try {
            forgotPasswordServicio.setPasswordToken(token, email);

            String resetPasswordLink = forgotPasswordServicio.getSiteURL(request) + "/forgot_password/reset_password?token=" + token;

            mailServicio.enviarEmail(email, resetPasswordLink);

            model.addAttribute("titulo", "Olvide la password");
            model.addAttribute("success", "Hemos enviado un enlace de restablecimiento de contraseña a su correo electrónico. Por favor, verifiquelo.");

            return "forgot-password-form";
        } catch (Exception ex) {
            model.addAttribute("titulo", "Olvide la password");
            model.addAttribute("error", ex.getMessage());
            return "forgot-password-form";
        }
    }

    @GetMapping("/reset_password")
    public String mostrarResetPassword(@RequestParam("token") String token, Model model) {

        Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
        if (u == null) {
            model.addAttribute("titulo", "Actualizar tu contraseña");
            model.addAttribute("error", "Token invalido");
            return "reset-password-form";
        }

        model.addAttribute("titulo", "Actualizar tu contraseña");
        model.addAttribute("token", token);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        try {
            Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
            if (u == null) {
                model.addAttribute("titulo", "Actualizar tu contraseña");
                model.addAttribute("error", "Token invalido");
                return "reset-password-form";
            } else {
                forgotPasswordServicio.actualizarPassword(u, password);
                model.addAttribute("titulo", "Actualizar tu contraseña");
                model.addAttribute("success", "Has cambiado satisfactoriamente tu contraseña");
            }
        } catch (Exception e) {
            model.addAttribute("titulo", "Actualizar tu contraseña");
            model.addAttribute("error", e.getMessage());
        }

        return "reset-password-form";
    }

}
