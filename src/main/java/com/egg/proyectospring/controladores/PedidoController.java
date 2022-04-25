/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.CarritoItem;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.enumeraciones.EstadoPedido;
import com.egg.proyectospring.servicios.CarritoItemServicio;
import com.egg.proyectospring.servicios.PedidoServicio;
import com.egg.proyectospring.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoServicio pedidoServicio;
    
    @Autowired
    private CarritoItemServicio carritoServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String pedido(Model modelo, Authentication auth){
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        
        Pedido pedido = pedidoServicio.mostrarPedidoUsuario(u.getId());
        modelo.addAttribute("carrito", pedido.getCarrito());
        modelo.addAttribute("pedido", pedido);
        return "pedido";
    }
    
    @PostMapping("/save")
    public String realizarPedido(Authentication auth) {
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        List<CarritoItem> lista = carritoServicio.carrito(u);
        Pedido pedido = new Pedido();
        pedido.setCarrito(lista);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFecha(new Date());
        pedido.setTotal(pedidoServicio.calcularTotal(lista));
        pedido.setUsuario(u);
        pedidoServicio.realizarPedido(pedido);
//        carritoServicio.eliminarProductos(lista);
        return "redirect:/carrito";
    }
    
    @GetMapping("/usuario")
    public String pedidoUsuario(Model modelo, Authentication auth) {
        Usuario u = usuarioServicio.mostrarUsuarioLogeado(auth);
        List<Pedido> pedidos = pedidoServicio.mostrarPedidosDeUnUsuario(u.getId());
        modelo.addAttribute("pedidos", pedidos);
        return "pedido-usuario";
    }
    
    @GetMapping("/usuario/pedido-detalles")
    public String detallesDelPedido(Model modelo, @RequestParam("id") String id) {
        
        Pedido pedido = pedidoServicio.mostrarPedidoPorId(id);
        
        modelo.addAttribute("pedido", pedido);
        return "pedido-detalles";
    }
    
    @GetMapping("/modificar")
    public String modificar(Model modelo, @RequestParam("id") String id, @RequestParam("estado") EstadoPedido estado) {
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
        return "lista-pedidos";
    }
    
}
