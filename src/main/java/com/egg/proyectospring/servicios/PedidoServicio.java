/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.servicios;

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
    
    public List<Pedido> pedidos() {
        return pedidoRepositorio.findAll();
    }
    
}
