package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Pedido;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {
    
    @Query("select p from Pedido p")
    public Page<Pedido> getAll(Pageable pagebale);
    
    @Query("select p from Pedido p where p.usuario.id = :uid")
    public Page<Pedido> pedidosPorUsuario(@Param("uid") String uid, Pageable pageable);
    
    
}
