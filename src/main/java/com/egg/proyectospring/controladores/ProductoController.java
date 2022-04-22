package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("")
    public String productoId(@RequestParam("prodId") String id, Model model) {

        try {
            Producto p = productoServicio.mostrarProductoPorId(id);
            model.addAttribute("producto", p);
            return "productoId";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }

    }

}
