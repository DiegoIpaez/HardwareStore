/*

 */
package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.servicios.CategoriaServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    
    @Autowired
    private CategoriaServicio categoriaServicio;
    
    @GetMapping("/form")
    public String formulario(Model modelo) {
        Categoria categoria = new Categoria();
        modelo.addAttribute("categoria", categoria);
        return "formulario-categoria";
    }
    
    @PostMapping("/save")
    public String form(@ModelAttribute("categoria") Categoria categoria, Model modelo) {
        
        try {
            categoriaServicio.guardarCategoria(categoria);
            String success = categoria.getId() != null && !categoria.getId().isEmpty() ? "Se modificó la categoría" : "Se ingresó una nueva categoría";
            modelo.addAttribute("success", success);
            
            return "formulario-categoria";
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("error", ex.getMessage());
            return "formulario-categoria";
        }
    }
    
    @GetMapping("/list")
    public String lista(Model modelo) {
        
        List<Categoria> categorias = categoriaServicio.listAll();
        modelo.addAttribute("listaDeCategorias", categorias);
        return "lista-categoria";
    }
    
    @GetMapping("/baja") 
    public String darDeBaja(@RequestParam("id") String id) {
        
        try {
            categoriaServicio.darDeBaja(id);
                     
            return "redirect:/categoria/list";
        } catch (Exception ex) {
            ex.printStackTrace();
           return "redirect:/categoria/list";
        }
    }
    
    @GetMapping("/alta")
    public String darDeAlta(@RequestParam("id") String id) {
        
        try {
            categoriaServicio.darDeAlta(id);
            
            return "redirect:/categoria/list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/categoria/list";
        }
    }
    
    @GetMapping("/modificar")
    public String modificar(@RequestParam("id") String id, Model modelo) {
        
        Categoria categoria = categoriaServicio.categoriaPorId(id);
        modelo.addAttribute("categoria", categoria);
        return "formulario-categoria";
    }
    
}
