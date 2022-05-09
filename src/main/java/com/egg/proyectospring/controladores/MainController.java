package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.servicios.CategoriaServicio;
import com.egg.proyectospring.servicios.MarcaServicio;
import com.egg.proyectospring.servicios.ProductoServicio;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;
    @Autowired
    private MarcaServicio marcaServicio;
    
     @GetMapping("")
    public String index(Model model){
        List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        List<Marca> marcas = marcaServicio.listarMarcas();
        List<Producto> productosRecientes = productoServicio.productosRecientes();
        List<Producto> productosMasVendidos = productoServicio.productosMasVendidos();
        List<Producto> ultimasUnidades = productoServicio.ultimasUnidades();
       
        model.addAttribute("categorias", categorias);
        model.addAttribute("marcas", marcas);
        model.addAttribute("productosRecientes", productosRecientes);
        model.addAttribute("productosMasVendidos", productosMasVendidos);
        model.addAttribute("ultimasUnidades", ultimasUnidades);
        
        
    return "index";
    }
    
}