package com.covec.mx.cev.entities.municipio;

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
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/all")
    public String allMunicipios(@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page,5);
        Page<Municipio> municipioPage = municipioService.getAll(pageRequest);
        int totalPages = municipioPage.getTotalPages();
        if (totalPages>0){
            List<Integer> pages = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas",pages);
        }
        model.addAttribute("municipio",new Municipio());
        model.addAttribute("municipios",municipioPage.getContent());
        return "municipio/municipiocrud";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("municipio") Municipio municipio){
        municipioService.save(municipio);
        return "redirect:/municipios/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("municipio") Municipio municipio){
        municipioService.update(municipio);
        return "redirect:/municipios/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        municipioService.delete(id);
        return "redirect:/municipios/all";
    }
}
