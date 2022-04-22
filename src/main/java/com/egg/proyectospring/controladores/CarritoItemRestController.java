package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.CarritoItemServicio;
import com.egg.proyectospring.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarritoItemRestController {

    @Autowired
    private CarritoItemServicio carritoItemServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/carrito/add/{prod}/{ctd}")
    public String añadirProducto(@PathVariable("prod") String productoId,
            @PathVariable("ctd") Integer cantidad,
            Authentication auth) {

        try {
            Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);

            if (u == null) {
                return "You must login  to add this product to your shopping cart";
            }
            Integer cantidadAñadida = carritoItemServicio.añadirProducto(productoId, cantidad, u);

            return cantidadAñadida + " artículo(s) de este producto se agregaron a su carrito de compras.";
        } catch (Exception e) {
            e.printStackTrace();
            return "el artículo(s) no existe";
        }
    }

}
