package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    public Producto buscarProductoPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.alta = true AND p.nombre = :nombre")
    public List<Producto> buscarProductoPorNombreYAlta(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.alta = true")
    public List<Producto> buscarProductosDeAlta();

    @Query("select p from Producto p where p.nombre != :nombre")
    public List<Producto> productoMenosUno(@Param("nombre") String nombre);

    @Query("select p from Producto p where p.nombre like %:nombre%")
    public List<Producto> buscarProducto(@Param("nombre") String nombre);
    
    @Query("select p from Producto p where p.categoria.id = :categoriaId")
    public List<Producto> obtenerProductosPorCategoria(@Param("categoriaId") String categoriaId);
    
    @Query("select p from Producto p where p.marca.id = :marcaId")
    public List<Producto> obtenerProductosPorMarca(@Param("marcaId") String marcaId);
}
