package com.covec.mx.cev.entities.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping("/all")
    public String allCategories(@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page,5);
        Page<Categoria> categoriaPage = service.getAll(pageRequest);
        int totalPages = categoriaPage.getTotalPages();
        if (totalPages>0){
            List<Integer> pages = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas",pages);
        }
        model.addAttribute("categoria",new Categoria());
        model.addAttribute("categorias",categoriaPage.getContent());
        return "categoria/categoriacrud";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("categoria") Categoria categoria){
        service.save(categoria);
        return "redirect:/categorias/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoria") Categoria categoria){
        service.update(categoria);
        return "redirect:/categorias/all";
    }

}
