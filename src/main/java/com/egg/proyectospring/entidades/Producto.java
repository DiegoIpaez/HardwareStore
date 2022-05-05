//Entidad producto
package com.egg.proyectospring.entidades;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Juan Manuel
 */
@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Boolean disponible;
    private Boolean alta;
    @ManyToOne 
    private Marca marca;
    @ManyToOne 
    private Categoria categoria;
    @OneToOne
    private Foto foto;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date fecha;

    
    
}
