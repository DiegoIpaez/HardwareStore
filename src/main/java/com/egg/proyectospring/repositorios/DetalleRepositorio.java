package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Detalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepositorio extends JpaRepository<Detalle, String> {

    //Carrito del usuario
    @Query("select c from Detalle c where c.usuario.id = :userId")
    public List<Detalle> carritoPorUsuario(@Param("userId") String userId);

    //Busca si existe el item del carrito
    @Query("select c from Detalle c where c.usuario.id = :uid and c.producto.id= :prodId")
    public Detalle carritoPorUsuarioYproducto(@Param("uid") String uid,
            @Param("prodId") String prodId);

    //Actualiza
    @Modifying
    @Query("update Detalle c set c.cantidad = :cantidad where c.producto.id = :prodId and c.usuario.id = :uid")
    public void actualizarCantidad(@Param("cantidad") Integer cantidad,
            @Param("prodId") String prodId,
            @Param("uid") String uid);
    
    @Modifying
    @Query("update Detalle c set c.subtotal = :subtotal where c.producto.id = :prodId and c.usuario.id = :uid")
    public void actualizarSubtotal(@Param("subtotal") Double subtotal,
            @Param("prodId") String prodId,
            @Param("uid") String uid);

    //Elimina
    @Modifying
    @Query("delete from Detalle c where c.producto.id= :prodId and c.usuario.id = :uid")
    public void eliminarProducto(@Param("prodId") String prodId,@Param("uid") String uid);
}
