//Controlador del Producto
package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.servicios.CategoriaServicio;
import com.egg.proyectospring.servicios.MarcaServicio;
import com.egg.proyectospring.servicios.ProductoServicio;
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

/**
 * @author Juan Manuel
 */
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
    public String mostrarProductos(Model model) {
        List<Producto> productos = productoServicio.listarProductos();
        model.addAttribute("productos", productos);
       
        return "producto-list";
    }

    @GetMapping("/form")
    public String formularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
         List<Marca> marcas = marcaServicio.listarMarcas();
        model.addAttribute("marcas", marcas);
         List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        model.addAttribute("categorias", categorias);
        return "formulario-producto";
    }

    @PostMapping("/save")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoServicio.guardarProducto(producto);
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

    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            productoServicio.altaProducto(id);
            return "redirect:/producto";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto";
        }
    }

    @GetMapping("/baja")
    public String baja(@RequestParam("id") String id) {
        try {
            productoServicio.bajaProducto(id);
            return "redirect:/producto";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto";
        }
    }

    @GetMapping("/disponible")
    public String disponible(@RequestParam("id") String id) {
        try {
            productoServicio.ProductoDisponible(id);
            return "redirect:/producto";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto";
        }
    }

    @GetMapping("/noDisponible")
    public String noDisponible(@RequestParam("id") String id) {
        try {
            productoServicio.ProductoNoDisponible(id);
            return "redirect:/producto";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto";
        }
    }

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

     @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam(name = "id", required = true) String id) {
        try {
            productoServicio.eliminarProducto(id);
            
            return "redirect:/producto";
        } catch (Exception e) {
             e.printStackTrace();
             return "redirect:/producto";  
        }
    }
}
