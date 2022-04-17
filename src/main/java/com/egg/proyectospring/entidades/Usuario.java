package com.egg.proyectospring.entidades;

import com.egg.proyectospring.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    private String email;
    private String username;
    private String password;
    @OneToOne
    private Foto foto;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String resetPasswordToken;
    private Boolean alta;
    
}
