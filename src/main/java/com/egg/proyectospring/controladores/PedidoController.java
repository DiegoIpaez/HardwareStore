package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Detalle;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.enumeraciones.EstadoPedido;
import com.egg.proyectospring.servicios.DetalleServicio;
import com.egg.proyectospring.servicios.PedidoServicio;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private DetalleServicio carritoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @PostMapping("/save")
    public String realizarPedido(Authentication auth, Model model, RedirectAttributes redirect){
        try {
            Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
            List<Detalle> carrito = carritoServicio.carrito(u);

            pedidoServicio.realizarPedido(u, carrito);
            carritoServicio.eliminarProductos(carrito);
            
            redirect.addFlashAttribute("success", "Se ha completado su compra!");
            return "redirect:/carrito";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "redirect:/carrito";
        }
    }

    @GetMapping("/usuario")
    public String pedidoUsuario(Model model, Authentication auth, @PageableDefault(page = 0, size = 2) Pageable pageable) {
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        Integer page = pageable.getPageNumber();
        Page<Pedido> pedidos = pedidoServicio.mostrarPedidosPorUsuario(u.getId(), pageable);
        Integer totalDePaginas = pedidos.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", paginas);
        }
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("actual", page);
        model.addAttribute("siguiente", page + 1);
        model.addAttribute("anterior", page - 1);
        model.addAttribute("ultima", totalDePaginas - 1);
        return "pedido-usuario";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("/detalle")
    public String detallesDelPedido(Model model, @RequestParam("detalle") String detalle, @RequestParam("total") Double total) throws Exception {

        model.addAttribute("detalle", pedidoServicio.mostrarDetalle(detalle));
        model.addAttribute("total", total);

        return "pedido-detalle";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/actualizar")
    public String modificar(Model model, @RequestParam("id") String id, @RequestParam("estado") EstadoPedido estado) {
        try {
            pedidoServicio.modificarEstadoPedido(id, estado);

            return "redirect:/pedido/list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/pedido/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/list")
    public String listaDePedidos(Model modelo, @PageableDefault(page = 0, size = 2) Pageable pageable) {
        Integer page = pageable.getPageNumber();
        Page<Pedido> pedidos = pedidoServicio.getAll(pageable);
        Integer totalDePaginas = pedidos.getTotalPages();
        if (totalDePaginas > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, totalDePaginas).boxed().collect(Collectors.toList());
            modelo.addAttribute("paginas", paginas);
        }
        modelo.addAttribute("pedidos", pedidos);
        modelo.addAttribute("actual", page);
        modelo.addAttribute("siguiente", page + 1);
        modelo.addAttribute("anterior", page - 1);
        modelo.addAttribute("ultima", totalDePaginas - 1);
        modelo.addAttribute("estados", EstadoPedido.values());
        return "lista-pedidos";
    }

}
