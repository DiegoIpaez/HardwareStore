package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Foto;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.enumeraciones.Rol;
import com.egg.proyectospring.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private FotoServicio fotoServicio;

    public List<Usuario> mostrarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Long mostrarCantidadDeUsuarios() {
        return usuarioRepositorio.count();
    }

    public Usuario mostrarUsuarioPorId(String id) throws Exception {

        Optional<Usuario> res = usuarioRepositorio.findById(id);

        if (res.isPresent()) {
            return res.get();
        } else {
            throw new Exception("No existe este Usuario");
        }
    }

    public void darDeBajaUsuario(String id) throws Exception {

        Usuario u = usuarioRepositorio.findById(id).orElse(new Usuario());

        u.setAlta(false);
        usuarioRepositorio.save(u);
    }

    public void darDeAltaUsuario(String id) throws Exception {

        Usuario u = usuarioRepositorio.findById(id).orElse(new Usuario());

        u.setAlta(true);
        usuarioRepositorio.save(u);
    }

    public void registrarUsuario(Usuario u, String password2, MultipartFile file) throws Exception {

        validarCampos(u.getId(), u.getUsername(), u.getEmail(), u.getPassword(), password2);

        Usuario ux = null;

        if (u.getId() != null && !u.getId().isEmpty()) {

            ux = mostrarUsuarioPorId(u.getId());
            ux.setUsername(u.getUsername());

            if (file != null && !file.isEmpty()) {
                Foto foto = fotoServicio.guardarFoto(file);
                ux.setFoto(foto);
            } else {
                ux.setFoto(mostrarUsuarioPorId(u.getId()).getFoto());
            }

        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            u.setPassword(encoder.encode(u.getPassword()));
            u.setRol(Rol.USUARIO);
            u.setAlta(true);

            if (file != null) {
                Foto foto = fotoServicio.guardarFoto(file);
                u.setFoto(foto);
            }
        }

        Usuario usuario = ux != null ? ux : u;

        usuarioRepositorio.save(usuario);
    }

    public void validarCampos(String id, String username, String email, String password, String password2) throws Exception {

        if (username == null || username.isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (id == null || id.isEmpty()) {

            if (email == null || email.isEmpty()) {
                throw new Exception("El email es obligatorio");
            }
                       
            if (email.length() > 30) {
                throw new Exception("La contraseña no puede tener mas de 30 caracteres");
            }

            if (usuarioRepositorio.buscarUsuarioPorEmailSinAlta(email) != null) {
                throw new Exception("Este email ya existe");
            }

            if (password == null || password.isEmpty()) {
                throw new Exception("La contraseña es obligatoria");
            }
            
            if (password.length() < 8) {
                throw new Exception("La contraseña debe tener como minimo 8 caracteres");
            }
            
            if (password.length() > 30) {
                throw new Exception("La contraseña no puede tener mas de 30 caracteres");
            }

            if (!password.equals(password2)) {
                throw new Exception("La contraseñas deben ser iguales");
            }
        }

    }

    public void agregarUsuarioALaSesion(Usuario usuario) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("usuario", usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            //Busco si existe el usuario que se quiere logear
            Usuario u = usuarioRepositorio.buscarUsuarioPorEmail(email);

            agregarUsuarioALaSesion(u);

            //Permisos de usuario
            List<GrantedAuthority> autorities = new ArrayList<>();

            if (u.getRol().equals(Rol.ADMINISTRADOR)) {
                autorities.add(new SimpleGrantedAuthority("ROLE_USUARIO"));
            }

            autorities.add(new SimpleGrantedAuthority("ROLE_" + u.getRol()));

            //Retorno User que necesita que le pasemos el nombre del usuario, su pass y los permisos
            return new User(u.getEmail(), u.getPassword(), autorities);

        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }

}
