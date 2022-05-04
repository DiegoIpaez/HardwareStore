package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.UsuarioServicio;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("")
    public String usuario(Model model, Authentication auth) {

        try {
            model.addAttribute("u", usuarioServicio.mostrarUsuarioLogeado(auth));
            return "usuario";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/list")
    public String listUsers(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
        Integer page = pageable.getPageNumber();
        Page<Usuario> usuarios = usuarioServicio.getAll(pageable);
        Integer totalDePaginas = usuarios.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", paginas);
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("actual", page);
        model.addAttribute("siguiente", page+1);
        model.addAttribute("anterior", page-1);
        model.addAttribute("ultima", totalDePaginas-1);
        return "lista-usuarios";
    }

    @GetMapping("/form")
    public String registro(Model model, @RequestParam(name = "usuarioId", required = false) String id) {

        Usuario u = new Usuario();
        String titulo = "";

        try {
            if (id != null) {
                u = usuarioServicio.mostrarUsuarioPorId(id);
                if (u.getId() != null) {
                    titulo = "Editar Usuario";
                    model.addAttribute("titulo", titulo);
                }
            } else {
                titulo = "Registrar Usuario";
            }

            model.addAttribute("titulo", titulo);
            model.addAttribute("usuario", u);
            return "usuario-formulario";
        } catch (Exception e) {
            model.addAttribute("codigo", "404");
            model.addAttribute("explicacion", e.getMessage());
            return "error";
        }

    }

    @PostMapping("/registro")
    public String usuarioPost(@ModelAttribute("usuario") Usuario u,
            @RequestParam(name = "password2", required = false) String password2,
            @RequestParam(name = "file", required = false) MultipartFile file,
            Model model) {

        try {
            usuarioServicio.registrarUsuario(u, password2, file);
            model.addAttribute("titulo", u.getId() == null || u.getId().isEmpty() ? "Registrar Usuario" : "Editar Usuario");
            model.addAttribute("success", u.getId() == null || u.getId().isEmpty() ? "Se ha registrado correctamente" : "Los cambios se han guardado correctamente");

            String direccion = "usuario-formulario";

            return direccion;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            model.addAttribute("titulo", u.getId() == null || u.getId().isEmpty() ? "Registrar Usuario" : "Editar Usuario");
            return "usuario-formulario";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/baja")
    public String darDeBaja(@RequestParam("usuarioId") String id) {

        try {
            usuarioServicio.darDeBajaUsuario(id);
            return "redirect:/usuario/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/usuario/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/alta")
    public String darDeAlta(@RequestParam("usuarioId") String id) {

        try {
            usuarioServicio.darDeAltaUsuario(id);
            return "redirect:/usuario/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/usuario/list";
        }
    }

}
