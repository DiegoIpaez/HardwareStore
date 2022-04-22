package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.repositorios.ProductoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public Producto mostrarProductoPorId(String id) throws Exception {

        Optional<Producto> res = productoRepositorio.findById(id);

        if (res.isPresent()) {
            return res.get();
        } else {
            throw new Exception("No existe este Producto");
        }
    }

}
