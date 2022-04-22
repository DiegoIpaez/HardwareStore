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
                return "Debe iniciar sesión para agregar este producto a su carrito de compras";
            }
            Integer cantidadAñadida = carritoItemServicio.añadirProducto(productoId, cantidad, u);

            return cantidadAñadida + " artículo(s) de este producto se agregaron a su carrito de compras.";
        } catch (Exception e) {
            e.printStackTrace();
            return "el artículo(s) no existe";
        }
    }

    @PostMapping("/carrito/actualizar/{prod}/{ctd}")
    public String actualizarCantidad(@PathVariable("prod") String productoId,
            @PathVariable("ctd") Integer cantidad,
            Authentication auth) {

        try {

            Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
            if (u == null) {
                return "Debe iniciar sesión para agregar este producto a su carrito de compras";
            }
            Double subtotal = carritoItemServicio.actualizarCantidad(cantidad, productoId, u);
            return String.valueOf(subtotal);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/carrito/eliminar/{prod}")
    public String eliminarProducto(@PathVariable("prod") String prodId,
            Authentication auth) {

        try {
            Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);

            if (u == null) {
                return "Debe iniciar sesión para elminar este producto de su carrito de compras";
            }

            carritoItemServicio.eliminarProducto(prodId, u);

            return "El producto ha sido eliminado de su carrito";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

}
