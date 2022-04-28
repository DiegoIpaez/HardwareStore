package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Detalle;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.DetalleServicio;
import com.egg.proyectospring.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carrito")
public class DetalleController {

    @Autowired
    private DetalleServicio detalleServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("")
    public String mostrarCarrito(Model model, Authentication auth) {

        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth); 
        
        List<Detalle> carrito = detalleServicio.carrito(u);

        model.addAttribute("carrito", carrito);

        return "carrito";
    }


}
