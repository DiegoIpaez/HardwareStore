/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.CarritoItem;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.repositorios.PedidoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {
    
    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    
    public Pedido realizarPedido(Pedido pedido){
        return pedidoRepositorio.save(pedido);
    }
    
    public Double calcularTotal(List<CarritoItem> carrito) {
        
        Double total = 0.0;
        Double aux = 0.0;
        for (CarritoItem carrito1 : carrito) {
            aux = carrito1.getSubtotal();
            total = aux + total;
        }
        
        return total;
        
    }
    
    public List<Pedido> pedidos() {
        return pedidoRepositorio.findAll();
    }
    
    public Pedido mostrarPedidoUsuario(String id) {
        return pedidoRepositorio.pedidoPorUsuario(id);
    }
    
    
}
