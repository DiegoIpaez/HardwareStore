//Controlador del Producto
package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.servicios.CategoriaServicio;
import com.egg.proyectospring.servicios.MarcaServicio;
import com.egg.proyectospring.servicios.ProductoServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoServicio productoServicio;
    @Autowired
    MarcaServicio marcaServicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    
    @GetMapping("")
    public String productoId(@RequestParam("prodId") String id, Model model) {

        try {
            Producto p = productoServicio.buscarProductoPorId(id);
            model.addAttribute("producto", p);
            return "productoId";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }

    }
    
    @GetMapping("/categoria")
    public String productoPorCategoria(@RequestParam("categoriaId") String id, Model model) {

        try {
            model.addAttribute("productos", productoServicio.listarProductosPorCategoria(id));
            model.addAttribute("titulo", categoriaServicio.categoriaPorId(id).getNombre());
            return "producto-por-catalogo";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }

    }
    
    @GetMapping("/marca")
    public String productoPorMarca(@RequestParam("marcaId") String id, Model model) {

        try {
            model.addAttribute("productos", productoServicio.listarProductosPorMarca(id));
            model.addAttribute("titulo", marcaServicio.buscarMarcaPorId(id).getNombre());
            return "producto-por-catalogo";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/list")
    public String mostrarProductos(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
        Integer page = pageable.getPageNumber();
        Page<Producto> productos = productoServicio.getAll(pageable);
        Integer totalDePaginas = productos.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", paginas);
        }
        model.addAttribute("productos", productos);
        model.addAttribute("actual", page);
        model.addAttribute("siguiente", page+1);
        model.addAttribute("anterior", page-1);
        model.addAttribute("ultima", totalDePaginas-1);
        return "producto-list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/form")
    public String formularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        List<Marca> marcas = marcaServicio.listarMarcas();
        model.addAttribute("marcas", marcas);
        List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        model.addAttribute("categorias", categorias);
        return "formulario-producto";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/save")
    public String guardarProducto(@ModelAttribute("producto") Producto producto,
            @RequestParam(name = "file", required = false) MultipartFile file,
            Model model) {
        try {

            productoServicio.guardarProducto(producto, file);
            String success = producto.getId() != null && !producto.getId().isEmpty() ? "Se modificó el producto" : "Se ingresó un nuevo producto";
            model.addAttribute("success", success);
            return "formulario-producto";
        } catch (Exception ex) {
            ex.printStackTrace();
            producto.setNombre(producto.getNombre());
            producto.setDescripcion(producto.getDescripcion());
            producto.setMarca(producto.getMarca());
            producto.setCategoria(producto.getCategoria());
            model.addAttribute("producto", producto);
            model.addAttribute("error", ex.getMessage());

        }

        return "formulario-producto";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            productoServicio.altaProducto(id);
            return "redirect:/producto/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/baja")
    public String baja(@RequestParam("id") String id) {
        try {
            productoServicio.bajaProducto(id);
            return "redirect:/producto/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/disponible")
    public String disponible(@RequestParam("id") String id) {
        try {
            productoServicio.ProductoDisponible(id);
            return "redirect:/producto/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/noDisponible")
    public String noDisponible(@RequestParam("id") String id) {
        try {
            productoServicio.ProductoNoDisponible(id);
            return "redirect:/producto/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/modificar")
    public String modificar(@RequestParam(name = "id", required = true) String id, Model modelo) throws Exception {
        Producto producto = productoServicio.buscarProductoPorId(id);
        modelo.addAttribute("producto", producto);
        List<Marca> marcas = marcaServicio.listarMarcas();
        modelo.addAttribute("marcas", marcas);
        List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        modelo.addAttribute("categorias", categorias);
        return "formulario-producto";
    }

    @GetMapping("/buscarProducto")
    public String listarProductos(Model modelo, @RequestParam("nombrep") String nombre) {
        List<Producto> productos;
        try {
            productos = productoServicio.buscarProducto(nombre);
            modelo.addAttribute("productosBuscados", productos);
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("error", ex.getMessage());
        }      
        return "producto-todos";
    }
    
    @GetMapping("/productos")
    public String mostrarTodosLosProductos(Model model, @PageableDefault(page = 0, size = 12) Pageable pageable) {
        Integer page = pageable.getPageNumber();
        Page<Producto> productos = productoServicio.getAll(pageable);
        Integer totalDePaginas = productos.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", paginas);
        }
        List<Producto> p = new ArrayList<>();
        model.addAttribute("productosBuscados", p);
        model.addAttribute("productos", productos);
        model.addAttribute("actual", page);
        model.addAttribute("siguiente", page+1);
        model.addAttribute("anterior", page-1);
        model.addAttribute("ultima", totalDePaginas-1);
        
        return "producto-todos";
    }

}
