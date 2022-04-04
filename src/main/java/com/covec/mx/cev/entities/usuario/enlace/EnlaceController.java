package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/enlaces")
public class EnlaceController {
    @Autowired
    private EnlaceService service;
    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/all")
    public String allCategories(@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page,5);
        Page<Enlace> pageObject = service.getAll(pageRequest);
        int totalPages = pageObject.getTotalPages();
        if (totalPages>0){
            List<Integer> pages = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas",pages);
        }
        model.addAttribute("municipios",municipioService.getAvailable());
        model.addAttribute("enlace",new Enlace());
        model.addAttribute("enlaces",pageObject.getContent());
        return "enlace/enlacecrud";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("enlace") Enlace enlace){
        List<Integer> caracteres = new ArrayList<>();
        for(int i = 1; i <=10; i++) {
            int getRandomValue = (int) (Math.random()*(10-1)) + 1;
            caracteres.add(getRandomValue);
        }
        StringBuilder str = new StringBuilder();
        for (Integer numero : caracteres) {
            str.append(numero);
        }
        enlace.setNumEmpleado(str.toString());
        enlace.setEnabled(true);
        service.save(enlace);
        return "redirect:/enlaces/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("enlace") Enlace enlace){
        service.update(enlace);
        return "redirect:/enlaces/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/enlaces/all";
    }
}
