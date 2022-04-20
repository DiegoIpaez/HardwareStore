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

    public void actualizarPassword(Usuario u, String newPassword) throws Exception {
        
        validarPassword(newPassword);
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(newPassword);

        u.setPassword(encodePassword);
        u.setResetPasswordToken(null);
        usuarioRepositorio.save(u);
    }

    public void actualizarEmail(Usuario u, String newEmail) throws Exception {

        validarEmail(newEmail);

        u.setEmail(newEmail);
        u.setResetPasswordToken(null);
        usuarioRepositorio.save(u);
    }

    public void validarPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new Exception("La contraseña es obligatoria");
        }

        if (password.length() < 8) {
            throw new Exception("La contraseña debe tener como minimo 8 caracteres");
        }

        if (password.length() > 30) {
            throw new Exception("La contraseña no puede tener mas de 30 caracteres");
        }
    }

    public void validarEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("El email es obligatorio");
        }

        if (email.length() > 30) {
            throw new Exception("La contraseña no puede tener mas de 30 caracteres");
        }

        if (usuarioRepositorio.buscarUsuarioPorEmailSinAlta(email) != null) {
            throw new Exception("Este email ya existe");
        }
    }

    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
