package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Object>{
    
}
