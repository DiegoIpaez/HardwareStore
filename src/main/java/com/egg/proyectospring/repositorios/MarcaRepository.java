//Repositorio de Marca
package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Marca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan Manuel
 */

@Repository
public interface MarcaRepository extends JpaRepository<Marca, String>{
    
    @Query("SELECT m FROM Marca m WHERE m.nombre = :nombre")
    public Marca buscarMarcaPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT m FROM Marca m WHERE m.alta = true AND m.nombre = :nombre")
    public List<Marca> buscarMarcasDadasDeAlta(@Param("nombre") String nombre);
}
