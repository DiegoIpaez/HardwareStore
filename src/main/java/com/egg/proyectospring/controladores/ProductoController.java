//Controlador del Producto
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @param model
     * @return
     */
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

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/form")
    public String formularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        List<Marca> marcas = marcaServicio.listarMarcas();
        model.addAttribute("marcas", marcas);
        List<Categoria> categorias = categoriaServicio.categoriasConAlta();
        model.addAttribute("categorias", categorias);
        return "formulario-producto";
    }

    /**
     *
     * @param producto
     * @param file
     * @param model
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param id
     * @param modelo
     * @return
     * @throws Exception
     */
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

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param modelo
     * @param nombre
     * @return
     */
    @GetMapping("/listaProductos")
    public String listarProductos(Model modelo, @RequestParam("nombre") String nombre) {
        List<Producto> productos = productoServicio.buscarProducto(nombre);
        modelo.addAttribute("productos", productos);
        return "producto-lista2";
    }

}
