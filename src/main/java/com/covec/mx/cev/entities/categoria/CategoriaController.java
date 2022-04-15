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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.covec.mx.cev.entities.operacion.OperacionService;
import com.covec.mx.cev.entities.usuario.administrador.Administrador;

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

    @Autowired
    private OperacionService operacionService;

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
    public String save(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result, RedirectAttributes attributes, HttpSession httpSession){
        Administrador session = (Administrador) httpSession.getAttribute("user");
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else {
            service.save(categoria);
            operacionService.guardarOperacion("Insert", session.getId(),"Sin datos previos", categoria.toStringCategoria());
            attributes.addFlashAttribute("mensaje", "Se ha registrado correctamente");
        }
        return "redirect:/categorias/all";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result, RedirectAttributes attributes, HttpSession httpSession){
        Administrador session = (Administrador) httpSession.getAttribute("user");
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else {
            Categoria anterior = service.getOne(categoria.getId());
            service.update(categoria);
            operacionService.guardarOperacion("Update", session.getId(), anterior.toStringCategoria(), categoria.toStringCategoria());
            attributes.addFlashAttribute("mensaje", "Se ha actualizado correctamente");
        }
        return "redirect:/categorias/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, HttpSession httpSession){
        Administrador session = (Administrador) httpSession.getAttribute("user");
        Categoria anterior = service.getOne(id);
        service.delete(id);
        operacionService.guardarOperacion("Delete", session.getId(), anterior.toStringCategoria(), "Sin datos actuales");
        return "redirect:/categorias/all";
    }

}
