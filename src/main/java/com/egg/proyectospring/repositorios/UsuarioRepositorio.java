package com.egg.proyectospring.repositorios;

import com.egg.proyectospring.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("select u from Usuario u where u.alta = true and u.email = :email")
    public Usuario buscarUsuarioPorEmail(@Param("email") String email);

}
