/*

 */
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.repositorios.CategoriaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicio {
    
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    
    public Categoria guardarCategoria(Categoria categoria) throws Exception {
        //modificar para que valide que no se ingrese una categoria que ya exista en la base de datos
        if(categoria.getNombre().isEmpty()) {
            throw new Exception("El campo 'nombre' no puede estar vacío.");
        }
        if(categoria.getId() != null && !categoria.getId().isEmpty()) {
            
            Categoria c = categoriaRepositorio.getById(categoria.getId());
            for (Categoria categoriasMenosUno : categoriaRepositorio.categoriasMenosUno(c.getNombre())) {
               if (categoriasMenosUno.getNombre().equals(categoria.getNombre())) {
                  throw new Exception("Ya existe una categoría con ese nombre.");
               }
            }
            
            categoria.setAlta(c.getAlta());
            return categoriaRepositorio.save(categoria);
        } else {
            Categoria categoriaNueva = categoriaPorNombre(categoria.getNombre());
            if (categoriaNueva != null) {
                throw new Exception("Ya existe una categoría con ese nombre.");
            } else {
                categoria.setAlta(true);
        
                return categoriaRepositorio.save(categoria);
            }
        }
        
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
    
    public Categoria categoriaPorId(String id) throws Exception{
        Optional<Categoria> respuesta = categoriaRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No existe la categoría solicitada");
        }
        
    }
    
    public Categoria categoriaPorNombre(String nombre) {
        return categoriaRepositorio.buscarCategoriaPorNombre(nombre);
    }
    
    public List<Categoria> categoriasMenosUno(String nombre) {
        return categoriaRepositorio.categoriasMenosUno(nombre);
    }
    
    public Page<Categoria> getAll(Pageable pageable){
        return categoriaRepositorio.getAll(pageable);
    }
    
}
