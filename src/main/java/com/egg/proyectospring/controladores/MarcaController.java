package com.egg.proyectospring.controladores;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.servicios.MarcaServicio;
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


@Controller
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    MarcaServicio marcaServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/modificar")
    public String formulario(@RequestParam("marcaId") String id, Model modelo) throws Exception {
        Marca marca = marcaServicio.buscarMarcaPorId(id);
        modelo.addAttribute("marca", marca);
        return "formulario-marca";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/form")
    public String registro(Model model) {
        model.addAttribute("marca", new Marca());
        return "formulario-marca";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/save")
    public String marcaFormulario(@ModelAttribute("marca") Marca marca,
            Model model) {
        try {
            marcaServicio.guardarMarca(marca);
            String success = marca.getId() != null && !marca.getId().isEmpty() ? "Se modificó correctamente la marca" : "Se ingresó una nueva marca";
            model.addAttribute("success", success);
            model.addAttribute("marca", marca);
            return "formulario-marca";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("marca", marca);
            model.addAttribute("error", ex.getMessage());
            return "formulario-marca";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            marcaServicio.altaMarca(id);
            return "redirect:/marca/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/marca/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/baja")
    public String baja(@RequestParam("id") String id) {
        try {
            marcaServicio.bajaMarca(id);
            return "redirect:/marca/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/marca/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/list")
    public String listaMarcas(Model modelo, @PageableDefault(page = 0, size = 2) Pageable pageable) {
        Integer page = pageable.getPageNumber();
        Page<Marca> marcas = marcaServicio.getAll(pageable);
        Integer totalDePaginas = marcas.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            modelo.addAttribute("paginas", paginas);
        }
        modelo.addAttribute("marcas", marcas);
        modelo.addAttribute("actual", page);
        modelo.addAttribute("siguiente", page+1);
        modelo.addAttribute("anterior", page-1);
        modelo.addAttribute("ultima", totalDePaginas-1);
        return "marca-list";
    }

}
