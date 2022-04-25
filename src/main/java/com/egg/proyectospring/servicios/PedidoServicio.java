/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.CarritoItem;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.enumeraciones.EstadoPedido;
import com.egg.proyectospring.repositorios.PedidoRepositorio;
import java.util.List;
import java.util.Optional;
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
    
    public Pedido mostrarPedidoPorId(String id) {
        return pedidoRepositorio.getById(id);
    }
    
    public List<Pedido> mostrarPedidosDeUnUsuario(String id){
        return pedidoRepositorio.pedidosPorUsuario(id);
    }
    
    public void modificarEstadoPedido(String id, EstadoPedido estado) throws Exception {
        Optional<Pedido> res = pedidoRepositorio.findById(id);
        if (res != null) {
            Pedido pedido = res.get();
            
            pedido.setEstado(estado);
        } else {
            throw new Exception("No se encontró el pedido");
        }
    }
    
}
