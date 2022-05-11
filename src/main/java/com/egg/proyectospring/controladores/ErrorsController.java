package com.egg.proyectospring.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error",
                    method = {RequestMethod.GET, RequestMethod.POST})
    public String error(Model model, HttpServletRequest request) {
        
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        
         model.addAttribute("codigo", statusCode);
        
        switch(statusCode){
            case 404:
            model.addAttribute("explicacion","El recurso solicitado no existe");    
            break;
            case 403:
            model.addAttribute("explicacion","No tiene los permisos necesarios para acceder");    
            break;  
            case 500:
            model.addAttribute("explicacion","La solicitud no ha podido ser procesada por el servidor");    
            break;  
        }  
        
        return "error";
    }

}