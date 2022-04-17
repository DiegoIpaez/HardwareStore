package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.ForgotPasswordServicio;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    private JavaMailSender mailSender;

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

            enviarEmail(email, resetPasswordLink);

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
   public String mostrarResetPassword(@RequestParam("token") String token, Model model){
      
      Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
      if(u == null){
         model.addAttribute("titulo","Actualizar tu contraseña");
         model.addAttribute("error","Token invalido");
         return "reset-password-form";
      }
      
      model.addAttribute("titulo","Actualizar tu contraseña");
      model.addAttribute("token", token);
      return "reset-password-form";
   }

   @PostMapping("/reset-password")
   public String processResetPassword(HttpServletRequest request, Model model){
     
     String token = request.getParameter("token"); 
     String password = request.getParameter("password");

     Usuario u = forgotPasswordServicio.usuarioResetPasswordToken(token);
     if(u == null){
         model.addAttribute("titulo","Actualizar tu contraseña");
         model.addAttribute("error","Token invalido");
         return "reset-password-form";
     }else{
       forgotPasswordServicio.actualizarPassword(u, password);
       model.addAttribute("titulo","Actualizar tu contraseña");
       model.addAttribute("success","Has cambiado satisfactoriamente tu contraseña");
     }

     return "reset-password-form";
   }

    public void enviarEmail(String email, String link) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("laferreteriatucumana@gmail.com", "FerreteriaTuc. Support");
        helper.setTo(email);

        String asunto = "Aquí está el enlace para restablecer su contraseña";
        String contenido = "<p>Hola,</p>"
                + "Ha solicitado restablecer su contraseña"
                + "Haga clic en el enlace de abajo para cambiar su clave"
                + "<a href=" + link + " >Cambiar mi contraseña.</a>"
                + "Ignore este correo electrónico si recuerda su contraseña o no ha realizado la solicitud";

        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(message);
    }

}
