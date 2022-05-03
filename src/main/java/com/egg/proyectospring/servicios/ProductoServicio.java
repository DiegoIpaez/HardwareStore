//Servicio del Producto
package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Categoria;
import com.egg.proyectospring.entidades.Foto;
import com.egg.proyectospring.entidades.Marca;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.repositorios.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Juan Manuel
 */
@Service
public class ProductoServicio {

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    MarcaServicio marcaServicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    FotoServicio fotoServicio;

    //metodo encargado de traer todos las productos
    /**
     * @return
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    //metodo se encarga de buscar un producto por id
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Producto buscarProductoPorId(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            return producto;
        } else {
            throw new Exception("No se encontro el Producto");
        }

    }

    //metodo que guarda, edita y crea un producto
    /**
     * @param producto
     * @param file
     * @return
     * @throws Exception
     */
    public Producto guardarProducto(Producto producto, MultipartFile file) throws Exception {
        validar(producto);

        if (producto.getId() != null && !producto.getId().isEmpty()) {
            producto.setId(producto.getId());

            Producto p = productoRepository.getById(producto.getId());
            for (Producto productoMenosUno : productoRepository.productoMenosUno(p.getNombre())) {
                if (productoMenosUno.getNombre().equals(producto.getNombre())) {
                    throw new Exception(" ya existe un producto con ese nombre");
                }
            }

            producto.setNombre(producto.getNombre());
            producto.setDescripcion(producto.getDescripcion());
            producto.setPrecio(producto.getPrecio());
            Marca marca = marcaServicio.buscarMarcaPorNombre(producto.getMarca().getNombre());
            if (marca != null) {
                producto.setMarca(marca);
            } else {
                throw new Exception("no existe esa marca");
            }
            Categoria categoria = categoriaServicio.categoriaPorNombre(producto.getCategoria().getNombre());
            if (categoria != null) {
                producto.setCategoria(categoria);
            } else {
                throw new Exception("no existe esa categoria");
            }
            Producto p1 = productoRepository.getById(producto.getId());
            producto.setAlta(p1.getAlta());
            producto.setDisponible(p1.getDisponible());

            if (file != null && !file.isEmpty()) {
                Foto foto = fotoServicio.guardarFoto(file);
                producto.setFoto(foto);
            } else {
                Producto pdb = productoRepository.getById(producto.getId());
                producto.setFoto(pdb.getFoto());
            }

        } else {
            Producto productodb = productoRepository.buscarProductoPorNombre(producto.getNombre());
            if (productodb != null) {
                throw new Exception("El producto se encuentra en la base de datos");
            } else {
                producto.setNombre(producto.getNombre());
                producto.setDescripcion(producto.getDescripcion());
                producto.setPrecio(producto.getPrecio());
                Marca marca = marcaServicio.buscarMarcaPorNombre(producto.getMarca().getNombre());
                if (marca != null) {
                    producto.setMarca(marca);
                } else {
                    throw new Exception("no existe esa marca");
                }
                Categoria categoria = categoriaServicio.categoriaPorNombre(producto.getCategoria().getNombre());
                if (categoria != null) {
                    producto.setCategoria(categoria);
                } else {
                    throw new Exception("no existe esa categoria");
                }

                producto.setAlta(true);
                producto.setDisponible(true);
                producto.setFoto(fotoServicio.guardarFoto(file));

            }

        }
        return productoRepository.save(producto);
    }
    //metodo de validacion

    /**
     * @param producto
     * @throws Exception
     */
    public void validar(Producto producto) throws Exception {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new Exception("el nombre no puede ser nulo o estar vacio");
        }
        if (producto.getDescripcion() == null || producto.getDescripcion().isEmpty()) {
            throw new Exception("La descripcion no pueda estar vacia");
        }
        if (producto.getMarca().getNombre() == null || producto.getMarca().getNombre().isEmpty()) {
            throw new Exception("La marca no puede estar nula");
        }
        if (producto.getCategoria().getNombre() == null || producto.getCategoria().getNombre().isEmpty()) {
            throw new Exception("La categoria no puede estar nula");
        }
    }

    //Alta de producto
    /**
     * @param id
     * @throws Exception
     */
    public void altaProducto(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setAlta(true);
            productoRepository.save(producto);
        } else {
            throw new Exception("No se encontró ese producto");
        }
    }

    //Baja de producto
    /**
     * @param id
     * @throws Exception
     */
    public void bajaProducto(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setAlta(false);
            productoRepository.save(producto);
        } else {
            throw new Exception("No se encontró ese producto");
        }
    }

    //Eliminar un producto
    /**
     * @param id
     * @throws Exception
     */
    public void eliminarProducto(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            productoRepository.delete(producto);
        } else {
            throw new Exception("no se encontro ese producto");
        }

    }

    //Setear un Producto como disponible
    /**
     * @param id
     * @throws Exception
     */
    public void ProductoDisponible(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setDisponible(true);
            productoRepository.save(producto);
        } else {
            throw new Exception("No se encontró ese producto");
        }
    }

    //Setear un Producto como No disponible
    /**
     * @param id
     * @throws Exception
     */
    public void ProductoNoDisponible(String id) throws Exception {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setDisponible(false);
            productoRepository.save(producto);
        } else {
            throw new Exception("No se encontró ese producto");
        }
    }

    //Buscador de Productos
    /**
     * @param nombre
     * @return
     */
    public List<Producto> buscarProducto(String nombre) {
        return productoRepository.buscarProducto(nombre);
    }

}
