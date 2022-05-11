package com.egg.proyectospring.controladores;

import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.servicios.ProductoServicio;
import com.egg.proyectospring.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/usuario")
    public ResponseEntity<byte[]> fotoPerfil(@RequestParam("idUsuario") String id) {

        try {
            Usuario usuario = usuarioServicio.mostrarUsuarioPorId(id);
            byte[] foto = usuario.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);           
        }

    }
    
    @GetMapping("/producto")
    public ResponseEntity<byte[]> imgProducto(@RequestParam("idProd") String id) {

        try {
            Producto producto = productoServicio.buscarProductoPorId(id);
            byte[] foto = producto.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);           
        }

    }

}