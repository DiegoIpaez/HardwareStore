package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Detalle;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.enumeraciones.EstadoPedido;
import com.egg.proyectospring.servicios.DetalleServicio;
import com.egg.proyectospring.servicios.PedidoServicio;
import com.egg.proyectospring.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoServicio pedidoServicio;  
    @Autowired
    private DetalleServicio carritoServicio;    
    @Autowired
    private UsuarioServicio usuarioServicio;

    
    @PostMapping("/save")
    public String realizarPedido(Authentication auth){
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        List<Detalle> carrito = carritoServicio.carrito(u);
        
        pedidoServicio.realizarPedido(u, carrito);
        carritoServicio.eliminarProductos(carrito);
        return "redirect:/carrito";
    }
    
    @GetMapping("/usuario")
    public String pedidoUsuario(Model model, Authentication auth) {
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        List<Pedido> pedidos = pedidoServicio.mostrarPedidosPorUsuario(u.getId());
        model.addAttribute("pedidos", pedidos);
        return "pedido-usuario";
    }
    
    @GetMapping("/detalle")
    public String detallesDelPedido(Model model, @RequestParam("detalle") String detalle, @RequestParam("total") Double total) throws Exception {
        
        model.addAttribute("detalle", pedidoServicio.mostrarDetalle(detalle));
        model.addAttribute("total", total);
        
        return "pedido-detalle";
    }
    
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
    
    @GetMapping("/list")
    public String listaDePedidos(Model modelo) {
        List<Pedido> pedidos = pedidoServicio.pedidos();
        modelo.addAttribute("pedidos", pedidos);
        modelo.addAttribute("estados",EstadoPedido.values());
        return "lista-pedidos";
    }
    
}
