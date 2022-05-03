//servicio Marca
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.repositorios.MarcaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan Manuel
 */
@Service
public class MarcaServicio {

    @Autowired
    MarcaRepository marcaRepository;

    //metodo encargado de traer todas las marcas
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    //metodo se encarga de buscar una marca por id
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Marca buscarMarcaPorId(String id) throws Exception {
        Optional<Marca> respuesta = marcaRepository.findById(id);
        if (respuesta.isPresent()) {
            Marca marca = respuesta.get();
            return marca;
        } else {
            throw new Exception("No se encontro la Marca");
        }

    }

    //metodo para crear, editar y guardar la marca en la DB
    /**
     * @param id
     * @param nombre
     * @return
     * @throws Exception
     */
    public Marca guardarMarca(String id, String nombre) throws Exception {

        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede ser nulo o estar vacío");
        }
        Marca marca = new Marca();
        if (id != null && !id.isEmpty()) {
            marca.setId(id);
            Marca marcaDB = marcaRepository.buscarMarcaPorNombre(nombre);
            if (marcaDB != null) {
                throw new Exception("La marca ya se encuentra en la base de datos");
            }
            marca.setNombre(nombre);
            marca.setAlta(true);
        } else {
            Marca marcaDB = marcaRepository.buscarMarcaPorNombre(nombre);
            if (marcaDB != null) {
                throw new Exception("La marca ya se encuentra en la base de datos");
            } else {
                marca.setNombre(nombre);
                marca.setAlta(true);
            }
        }

        return marcaRepository.save(marca);

    }

    //Metodo para dar de Alta una marca
    /**
     * @param id
     * @throws Exception
     */
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

    //metodo para dar de baja una marca
    /**
     * @param id
     * @throws Exception
     */
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

    //metodo para eliminar una marca
    /**
     * @param id
     * @throws Exception
     */
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
