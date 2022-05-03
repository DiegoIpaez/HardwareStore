/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Manuel
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    /**
     * @param nombre
     * @return
     */
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    public Producto buscarProductoPorNombre(@Param("nombre") String nombre);

    /**
     * @param nombre
     * @return
     */
    @Query("SELECT p FROM Producto p WHERE p.alta = true AND p.nombre = :nombre")
    public List<Producto> buscarProductoPorNombreYAlta(@Param("nombre") String nombre);

    /**
     * @return
     */
    @Query("SELECT p FROM Producto p WHERE p.alta = true")
    public List<Producto> buscarProductosDeAlta();

    /**
     * @param nombre
     * @return
     */
    @Query("select p from Producto p where p.nombre != :nombre")
    public List<Producto> productoMenosUno(@Param("nombre") String nombre);

    /**
     * @param nombre
     * @return
     */
    @Query("select p from Producto p where p.nombre like %:nombre%")
    public List<Producto> buscarProducto(@Param("nombre") String nombre);

    @Query("select p from Producto p")
    public Page<Producto> getAll(Pageable pageable);
}
