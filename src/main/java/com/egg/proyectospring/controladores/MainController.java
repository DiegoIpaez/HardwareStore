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
    public String index(Model model){
        List<Producto> productosRecientes = productoServicio.productosRecientes();
        List<Producto> productosMasVendidos = productoServicio.productosMasVendidos();
        List<Producto> ultimasUnidades = productoServicio.ultimasUnidades();
       
        model.addAttribute("productosRecientes", productosRecientes);
        model.addAttribute("productosMasVendidos", productosMasVendidos);
        model.addAttribute("ultimasUnidades", ultimasUnidades); 
    return "index";
    }
    
    @GetMapping("/about")
    public String about(){
    return "about";
    }

}
