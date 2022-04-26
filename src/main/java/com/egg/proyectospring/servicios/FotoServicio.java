package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Foto;
import com.egg.proyectospring.repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardarFoto(MultipartFile file) throws Exception {

        if (file != null && !file.isEmpty()) {
            try {
                Foto f = new Foto();
                f.setName(file.getName());
                f.setMime(file.getContentType());
                f.setContenido(file.getBytes());

                return fotoRepositorio.save(f);
                
            } catch (Exception e) {
                throw new Exception("No se pudo guardar");
            }
        } else {
            return null;
        }

    }

}
