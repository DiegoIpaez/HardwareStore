package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.repositorios.MarcaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarcaServicio {

    @Autowired
    MarcaRepository marcaRepository;

    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    public Marca buscarMarcaPorId(String id) throws Exception {
        Optional<Marca> respuesta = marcaRepository.findById(id);
        if (respuesta.isPresent()) {
            Marca marca = respuesta.get();
            return marca;
        } else {
            throw new Exception("No se encontro la Marca");
        }

    }

    public Marca guardarMarca(Marca marca) throws Exception {

        if (marca.getNombre() == null || marca.getNombre().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }

        if (marca.getId() != null && !marca.getId().isEmpty()) {
            Marca marcaDB = marcaRepository.buscarMarcaPorNombre(marca.getNombre());
            if (marcaDB != null) {
                throw new Exception("Ya existe una marca con ese nombre.");
            }
            marca.setAlta(true);
        } else {
            Marca marcaDB = marcaRepository.buscarMarcaPorNombre(marca.getNombre());
            if (marcaDB != null) {
                throw new Exception("Ya existe una marca con ese nombre.");
            } else {
                marca.setAlta(true);
            }
        }

        return marcaRepository.save(marca);
    }

    public void altaMarca(String id) throws Exception {
        Optional<Marca> respuesta = marcaRepository.findById(id);
        if (respuesta.isPresent()) {
            Marca marca = respuesta.get();
            marca.setAlta(true);
            marcaRepository.save(marca);
        } else {
            throw new Exception("No se encontró esa marca");
        }
    }

    public void bajaMarca(String id) throws Exception {
        Optional<Marca> respuesta = marcaRepository.findById(id);
        if (respuesta.isPresent()) {
            Marca marca = respuesta.get();
            marca.setAlta(false);
            marcaRepository.save(marca);
        } else {
            throw new Exception("No se encontró esa marca");
        }
    }

    public void eliminarMarca(String id) throws Exception {
        Optional<Marca> respuesta = marcaRepository.findById(id);
        if (respuesta.isPresent()) {
            Marca marca = respuesta.get();
            marcaRepository.delete(marca);
        } else {
            throw new Exception("no se encontro esa marca");
        }

    }

    public Marca buscarMarcaPorNombre(String nombre) {
        return marcaRepository.buscarMarcaPorNombre(nombre);
    }
    
    public Page<Marca> getAll(Pageable pageable){
        return marcaRepository.getAll(pageable);
    }
}
