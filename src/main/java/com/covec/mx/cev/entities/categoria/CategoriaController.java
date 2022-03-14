package com.covec.mx.cev.entities.categoria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @GetMapping("/all")
    public String allCategories(){
        return "categoria/categoriacrud";
    }

}
