package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.CarritoItem;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.repositorios.CarritoItemRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CarritoItemServicio {

    @Autowired
    private CarritoItemRepositorio carritoItemRepositorio;
    @Autowired
    ProductoServicio productoServicio;

    public List<CarritoItem> carrito(Usuario u) {
        return carritoItemRepositorio.carritoPorUsuario(u.getId());
    }

    public Integer añadirProducto(String prodId, Integer cantidad, Usuario u) throws Exception {

        Integer cantidadAñadida = cantidad;
        CarritoItem carritoItem = carritoItemRepositorio.carritoPorUsuarioYproducto(u.getId(), prodId);

        if (carritoItem != null) {
            cantidadAñadida = carritoItem.getCantidad() + cantidad;
            carritoItem.setCantidad(cantidadAñadida);
        } else {
            carritoItem = new CarritoItem();
            carritoItem.setCantidad(cantidad);
            carritoItem.setProducto(productoServicio.mostrarProductoPorId(prodId));
            carritoItem.setUsuario(u);
        }

        Double subtotal = carritoItem.getProducto().getPrecio() * carritoItem.getCantidad();
        carritoItem.setSubtotal(subtotal);

        carritoItemRepositorio.save(carritoItem);

        return cantidadAñadida;
    }

    public Double actualizarCantidad(Integer cantidad, String prodId, Usuario u) throws Exception {

        Producto p = productoServicio.mostrarProductoPorId(prodId);
        Double subtotal = p.getPrecio() * cantidad;

        carritoItemRepositorio.actualizarCantidad(cantidad, prodId, u.getId());
        carritoItemRepositorio.actualizarSubtotal(subtotal, prodId, u.getId());

        return subtotal;
    }

    public void eliminarProducto(String prodId, Usuario u) {
        carritoItemRepositorio.eliminarProducto(prodId, u.getId());
    }

}
