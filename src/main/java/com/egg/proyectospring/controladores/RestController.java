package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.servicios.CategoriaServicio;
import com.egg.proyectospring.servicios.MarcaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    
    @Autowired
    private CategoriaServicio categoriaServicio;
    @Autowired
    private MarcaServicio marcaServicio;
    
    @GetMapping(path = "/categorias", produces = "application/json")
    public @ResponseBody List<Categoria> listaCategorias(){
        return categoriaServicio.categoriasConAlta();
    }
    
    @GetMapping(path = "/marcas", produces = "application/json")
    public @ResponseBody List<Marca> listaMarcas(){
        return marcaServicio.listarMarcas();
    }
}
