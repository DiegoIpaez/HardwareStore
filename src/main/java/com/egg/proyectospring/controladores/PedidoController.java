/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.servicios.PedidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoServicio pedidoServicio;
    
    @GetMapping("")
    public String pedido(Model modelo){
        
        return "pedido";
    }
    
    @PostMapping("/save")
    public String realizarPedido(@ModelAttribute("pedido") Pedido pedido) {
        
        return "pedido";
    }
    
    @GetMapping("/list")
    public String listaDePedidos(Model modelo) {
        List<Pedido> pedidos = pedidoServicio.pedidos();
        modelo.addAttribute("pedidos", pedidos);
        return "pedidos-list";
    }
    
}
