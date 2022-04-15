/*

 */
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.repositorios.CategoriaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicio {
    
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    
    public Categoria guardarCategoria(Categoria categoria) throws Exception {
        
        if(categoria.getNombre().isEmpty()) {
            throw new Exception("El campo 'nombre' no puede estar vacío");
        }
        categoria.setAlta(true);
        
        return categoriaRepositorio.save(categoria);
    }
    
    
    public void darDeBaja(String id) throws Exception {
        Optional<Categoria> respuesta = categoriaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Categoria categoria = respuesta.get();
            
            categoria.setAlta(false);
            categoriaRepositorio.save(categoria);
        } else {
            throw new Exception("No hay una categoría existente para ese identificador");
        }
    }
    
    public void darDeAlta(String id) throws Exception {
        Optional<Categoria> respuesta = categoriaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Categoria categoria = respuesta.get();
            
            categoria.setAlta(true);
            categoriaRepositorio.save(categoria);
        } else {
            throw new Exception("No hay una categoría existente para ese identificador");
        }
    }
    
    public List<Categoria> categoriasConAlta(){
        return categoriaRepositorio.buscarCategoriasConAlta(); 
    }
    
    public List<Categoria> listAll(){
        return categoriaRepositorio.findAll();
    }
    
    public Categoria categoriaPorId(String id){
        return categoriaRepositorio.getById(id);
    }
    
    
    
}
