package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Detalle;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.entidades.Usuario;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.proyectospring.repositorios.DetalleRepositorio;

@Service
@Transactional
public class DetalleServicio {

    @Autowired
    private DetalleRepositorio detalleRepositorio;
    @Autowired
    ProductoServicio productoServicio;

    public List<Detalle> carrito(Usuario u) {
        return detalleRepositorio.carritoPorUsuario(u.getId());
    }

    public Integer añadirProducto(String prodId, Integer cantidad, Usuario u) throws Exception {

        Integer cantidadAñadida = cantidad;
        Detalle carritoItem = detalleRepositorio.carritoPorUsuarioYproducto(u.getId(), prodId);

        if (carritoItem != null) {
            cantidadAñadida = carritoItem.getCantidad() + cantidad;
            carritoItem.setCantidad(cantidadAñadida);
        } else {
            carritoItem = new Detalle();
            carritoItem.setCantidad(cantidad);
            carritoItem.setProducto(productoServicio.buscarProductoPorId(prodId));
            carritoItem.setUsuario(u);
        }

        Double subtotal = carritoItem.getProducto().getPrecio() * carritoItem.getCantidad();
        carritoItem.setSubtotal(subtotal);

        detalleRepositorio.save(carritoItem);

        return cantidadAñadida;
    }

    public Double actualizarCantidad(Integer cantidad, String prodId, Usuario u) throws Exception {

        Producto p = productoServicio.buscarProductoPorId(prodId);
        Double subtotal = p.getPrecio() * cantidad;

        detalleRepositorio.actualizarCantidad(cantidad, prodId, u.getId());
        detalleRepositorio.actualizarSubtotal(subtotal, prodId, u.getId());

        return subtotal;
    }

    public void eliminarProducto(String prodId, Usuario u) {
        detalleRepositorio.eliminarProducto(prodId, u.getId());
    }
    
    public void eliminarProductos(List<Detalle> c){
    
        for (Detalle carrito : c) {
            detalleRepositorio.eliminarProducto(carrito.getProducto().getId(), carrito.getUsuario().getId());
        }
        
    }


}
