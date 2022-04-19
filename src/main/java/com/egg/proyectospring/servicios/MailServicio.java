package com.egg.proyectospring.servicios;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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
    
    public void contacto(String nombre, String email, String asunto, String contenido) throws Exception{
    
        //Instancio la clase que va a ser el cuerpo de mi mail
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        
        validarCampos(nombre, email, asunto, contenido);
        
        //Seteo la informacion que necesito
        simpleMailMessage.setTo("laferreteriatucumana@gmail.com");
        simpleMailMessage.setSubject(email + " ("+ nombre + ") - " + asunto);
        simpleMailMessage.setText(contenido);
        
        //La envio a traves de la clase..
        mailSender.send(simpleMailMessage);
        
    }
    
    public void validarCampos(String nombre, String email, String asunto, String contenido) throws Exception{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }
        
        if (email == null || email.isEmpty()) {
            throw new Exception("El email es obligatorio");
        }
        if (asunto == null || asunto.isEmpty()) {
            throw new Exception("El asunto es obligatorio");
        }
        if (contenido == null || contenido.isEmpty()) {
            throw new Exception("El contenido es obligatorio");
        }
    
    }
    
}
