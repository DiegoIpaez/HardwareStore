package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.servicios.ProductoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("")
    public String index(Model model) {
        List<Producto> productos = productoServicio.productosLimitados();

        model.addAttribute("productos", productos);
        return "index";
    }

}
