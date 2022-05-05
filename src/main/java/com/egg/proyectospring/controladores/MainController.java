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
    public String index(Model model, @PageableDefault(page = 0, size = 6) Pageable pageable){
        List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        List<Marca> marcas = marcaServicio.listarMarcas();
        
        Integer page = pageable.getPageNumber();
        Page<Producto> productos = productoServicio.getProductosPorFecha(pageable);
        Integer totalDePaginas = productos.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", paginas);
        }
        model.addAttribute("marcas", marcas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("actual", page);
        model.addAttribute("siguiente", page+1);
        model.addAttribute("anterior", page-1);
        model.addAttribute("ultima", totalDePaginas-1);
    return "index";
    }
    
}