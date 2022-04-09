package com.covec.mx.cev.entities.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public String save(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else {
            service.save(categoria);
            attributes.addFlashAttribute("mensaje", "Se ha registrado correctamente");
        }
        return "redirect:/categorias/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoria") Categoria categoria){
        service.update(categoria);
        return "redirect:/categorias/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/categorias/all";
    }

}
