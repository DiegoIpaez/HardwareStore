package com.egg.proyectospring.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping("")
    public String index(@RequestParam(name = "error", required = false) String error, Model model){
        
        if (error != null) {
            model.addAttribute("error", "El email o la contraseña estan incorrectos");
        }
        
    return "login";
    }
}
