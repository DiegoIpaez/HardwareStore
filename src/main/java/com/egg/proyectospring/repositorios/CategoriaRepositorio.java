/*
 
 */
package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, String>{
    
    @Query("select c from Categoria c where c.alta = true")
    public List<Categoria> buscarCategoriasConAlta();
    
    @Query("select c from Categoria c where c.nombre = :nombre")
    public Categoria buscarCategoriaPorNombre(@Param("nombre") String nombre);
    
    @Query("select c from Categoria c where c.nombre != :nombre")
    public List<Categoria> categoriasMenosUno(@Param("nombre") String nombre);

    
}
