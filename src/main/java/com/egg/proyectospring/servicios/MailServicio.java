package com.egg.proyectospring.servicios;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServicio {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void enviarEmail(String email, String link) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("laferreteriatucumana@gmail.com", "FerreteriaTuc. Support");
        helper.setTo(email);

        String asunto = "Aquí está el enlace para restablecer su contraseña";
        String contenido = "<p>Hola,</p>"
                + "Ha solicitado restablecer su contraseña.\n"
                + "Haga clic en el enlace de abajo para cambiar su clave. \n" 
                + "<a href=" + link + " >Cambiar mi contraseña.</a> \n"  
                + "Ignore este correo electrónico si recuerda su contraseña o no ha realizado la solicitud";

        helper.setSubject(asunto);
        helper.setText(contenido, true);

        mailSender.send(message);
    }
    
}
