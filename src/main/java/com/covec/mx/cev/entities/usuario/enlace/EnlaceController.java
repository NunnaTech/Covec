package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.MunicipioService;
import com.covec.mx.cev.entities.operacion.OperacionService;
import com.covec.mx.cev.entities.usuario.administrador.Administrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/enlaces")
public class EnlaceController {
    @Autowired
    private EnlaceService service;
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private OperacionService operacionService;

    @GetMapping("/all")
    public String allCategories(@RequestParam Map<String, Object> params, Model model) {
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Enlace> pageObject = service.getAll(pageRequest);
        int totalPages = pageObject.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        model.addAttribute("municipios", municipioService.getAllMunicipios());
        model.addAttribute("enlace", new Enlace());
        model.addAttribute("enlaces", pageObject.getContent());
        return "enlace/enlacecrud";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("enlace") Enlace enlace, Model model, HttpSession httpSession) {
        Administrador sesion = (Administrador) httpSession.getAttribute("user");
        List<Integer> caracteres = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int getRandomValue = (int) (Math.random() * (10 - 1)) + 1;
            caracteres.add(getRandomValue);
        }
        StringBuilder str = new StringBuilder();
        for (Integer numero : caracteres) {
            str.append(numero);
        }
        enlace.setNumEmpleado(str.toString());
        String encode = passwordEncoder.encode(enlace.getPassword());
        enlace.setTipoUsuario("Enlace");
        enlace.setEnabled(true);
        enlace.setPassword(encode);
        String actual = String.format(
                "enlace:{nombreCompleto:'%s',username:'%s',telefono:'%s',tipoUsuario:'%s', enabled:%s, numEmpleado:'%s'}",
                enlace.getNombreCompleto(), enlace.getUsername(), enlace.getTelefono(), enlace.getTipoUsuario(),
                enlace.getEnabled(), enlace.getNumEmpleado());
        operacionService.guardarOperacion("Insert", sesion.getId(), "enlace:{datos:'Sin datos previos'}", actual);
        service.save(enlace);
        return "redirect:/enlaces/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("enlace") Enlace enlace, HttpSession httpSession) {
        Administrador sesion = (Administrador) httpSession.getAttribute("user");
        Enlace old = service.getOne(enlace.getId());
        String anterior = String.format(
                "enlace:{nombreCompleto:'%s',username:'%s',telefono:'%s',tipoUsuario:'%s', enabled:%s, numEmpleado:'%s'}",
                old.getNombreCompleto(), old.getUsername(), old.getTelefono(), old.getTipoUsuario(),
                old.getEnabled(), old.getNumEmpleado());
        Enlace updated = service.update(enlace);
        String actual = String.format(
                "enlace:{nombreCompleto:'%s',username:'%s',telefono:'%s',tipoUsuario:'%s', enabled:%s, numEmpleado:'%s'}",
                updated.getNombreCompleto(), updated.getUsername(), updated.getTelefono(), updated.getTipoUsuario(),
                updated.getEnabled(), updated.getNumEmpleado());
        operacionService.guardarOperacion("Insert", sesion.getId(), anterior, actual);
        return "redirect:/enlaces/all";
    }

    @GetMapping("/remove/{id}/{opc}")
    public String delete(@PathVariable Integer id,@PathVariable Integer opc, HttpSession httpSession) {
        Administrador sesion = (Administrador) httpSession.getAttribute("user");
        Enlace old = service.getOne(id);
        String anterior = String.format("enlace:{username:'%s',enabled:%s}",old.getUsername(), old.getEnabled());
        Enlace updated = service.delete(id, opc);
        String actual = String.format("enlace:{username:'%s',enabled:%s}",updated.getUsername(), updated.getEnabled());
        operacionService.guardarOperacion("Update", sesion.getId(), anterior, actual);
        return "redirect:/enlaces/all";
    }
}
