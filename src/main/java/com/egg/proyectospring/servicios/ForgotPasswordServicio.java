package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.repositorios.UsuarioRepositorio;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void setPasswordToken(String token, String email) throws Exception {
        Usuario u = usuarioRepositorio.buscarUsuarioPorEmail(email);

        if (u != null) {
            u.setResetPasswordToken(token);
            usuarioRepositorio.save(u);
        } else {
            throw new Exception("El usuario ingresado no existe");
        }

    }

    public Usuario usuarioResetPasswordToken(String resetPasswordToken) {
        return usuarioRepositorio.mostrarUsuarioPorResetPasswordToken(resetPasswordToken);
    }

    public void actualizarPassword(Usuario u, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(newPassword);

        u.setPassword(encodePassword);
        u.setResetPasswordToken(null);
        usuarioRepositorio.save(u);
    }
    
    public String getSiteURL(HttpServletRequest request){
       String siteURL = request.getRequestURL().toString();
       return siteURL.replace(request.getServletPath(), "");
  }
}
