package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.MunicipioService;
import com.covec.mx.cev.entities.rol.Rol;
import com.covec.mx.cev.entities.rol.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/enlaces")
public class EnlaceController {
    @Autowired
    private EnlaceService service;
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private RolService rolService;
    @Autowired
    PasswordEncoder passwordEncoder;

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
    public String save(@Valid @ModelAttribute("enlace") Enlace enlace, BindingResult result, RedirectAttributes attributes){
       if (result.hasErrors()){
           List<String> errores = new ArrayList<>();
           for (ObjectError error:result.getAllErrors()) {
               errores.add(error.getDefaultMessage());
           }
        attributes.addFlashAttribute("errores", errores);
       }else {
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
           enlace.setTipoUsuario("Enlace");
           enlace.setEnabled(true);
           String encode = passwordEncoder.encode(enlace.getPassword());
           enlace.setPassword(encode);
           Rol rolUsuario = rolService.getOne(2);
           Set<Rol> roles = new HashSet<>();
           roles.add(rolUsuario);
           enlace.setRoles(roles);
           service.save(enlace);
           attributes.addFlashAttribute("mensaje", "Se ha registrado correctamente");
       }
        return "redirect:/enlaces/all";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("enlace") Enlace enlace, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else{
            service.update(enlace);
            attributes.addFlashAttribute("mensaje", "Se ha actualizado correctamente");
        }
        return "redirect:/enlaces/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/enlaces/all";
    }

}
