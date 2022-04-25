/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {
    
    @Query("select p from Pedido p")
    public List<Pedido> listaDePedidos();
    
    @Query("select p from Pedido p where p.usuario.id = :uid")
    public Pedido pedidoPorUsuario(@Param("uid") String uid);
    
    @Query("select p from Pedido p where p.usuario.id = :uid")
    public List<Pedido> pedidosPorUsuario(@Param("uid") String uid);
    
}
