package com.egg.proyectospring.controladores;

import com.egg.proyectospring.servicios.MailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private MailServicio mailServicio;

    @GetMapping("")
    public String contacto() {
        return "contacto";
    }

    @PostMapping("/enviar")
    public String contactanos(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("asunto") String asunto,
            @RequestParam("contenido") String contenido,
            Model model) {

        try {
            mailServicio.contacto(nombre, email, asunto, contenido);
            model.addAttribute("success", "Se ha enviado exitosamente su mail");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "contacto";
    }

}
