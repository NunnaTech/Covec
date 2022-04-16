package com.covec.mx.cev.entities.comite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.covec.mx.cev.entities.colonia.Colonia;
import com.covec.mx.cev.entities.colonia.ColoniaService;
import com.covec.mx.cev.entities.operacion.OperacionService;
import com.covec.mx.cev.entities.rol.Rol;
import com.covec.mx.cev.entities.rol.RolService;
import com.covec.mx.cev.entities.usuario.UsuarioService;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import com.covec.mx.cev.entities.usuario.integrante.IntegranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comites")
public class ComiteController {

    @Autowired
    private ComiteService comiteService;

    @Autowired
    private ColoniaService coloniaService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private IntegranteService integranteService;

    @Autowired
    private RolService rolService;

    @Autowired
    private OperacionService operacionService;  

    @Autowired
    private UsuarioService usuarioService;


    private List<Integer>listIds = new LinkedList<>(); 
    private List<Integrante> integrantes = new LinkedList<>();
    private static final String CONST_COMITE ="comite";
    private static final String CONST_INTEGRANTE="integrante";
    private static final String CONST_INTEGRANTES="integrantes";
    private Comite comite = new Comite();
    private Integer coloniaID;
    

    @GetMapping("/listar/{id}")
    public String getAll(Model model, @PathVariable("id") Integer id, @RequestParam Map<String, Object> params) {
        // Obtenemos el contexto(número) actual de la vista(página)
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

        // Mandamos una consulta al repositorio en base al número actual(0) de la vista
        // (0-5), numeramos de 5 en 5
        // (0-5) (5-10) (10-15)
        Page<Comite> comitePages = comiteService.getAll(id,PageRequest.of(page, 5));

        if (comitePages.getTotalPages() > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, comitePages.getTotalPages()).boxed()
                    .collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        coloniaID = id;
        listIds.clear();
        integrantes.clear();
        model.addAttribute("colonia", coloniaService.getOne(id));
        model.addAttribute(CONST_COMITE, new Comite());
        model.addAttribute("comites", comitePages.getContent());
        return "comite/comitecrud";
    }
    

    /**
     * Vista para listar los integrantes existentes de un comite
     */
    @GetMapping("/integrantes/listar/{id}")
    public String seeMembers(@PathVariable("id") Integer id, Model model){
        comite = comiteService.getOne(id);
        integrantes = comite.getIntegrantes();
        model.addAttribute(CONST_INTEGRANTE, new Integrante());
        model.addAttribute(CONST_INTEGRANTES,integrantes);
        model.addAttribute(CONST_COMITE,comite);
        return "comite/integrantecrud";
    }

    /**
     * Vista para crear un nuevo comite 
     */
    @GetMapping("/integrantes/nuevo")
    public String newGroup(Model model){
        model.addAttribute("ID",coloniaID);
        model.addAttribute(CONST_INTEGRANTE, new Integrante());
        return "comite/crearcomite";
    }

    /**
     * Método para crear un integrante | enlace
     */
    @PostMapping("/integrantes/agregar")
    public String add(@Valid Integrante integrante, BindingResult result, RedirectAttributes attributes, Model model, HttpSession httpSession){
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        Colonia colonia = coloniaService.getOne(coloniaID);
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else {
            Comite nuevoComite = new Comite();
            integrante.setComite(nuevoComite);
            integrante.setEnabled(true);
            integrante.setTipoUsuario("Presidente");
            integrante.setPresidente(true);
            String encode = passwordEncoder.encode(integrante.getPassword());
            integrante.setPassword(encode);
            Rol rolUsuario = rolService.getOne(3);
            Set<Rol> roles = new HashSet<>();
            roles.add(rolUsuario);
            integrante.setRoles(roles);

            integrantes.add(integrante);
            
            nuevoComite.setIntegrantes(integrantes);
            nuevoComite.setColonia(colonia);
            nuevoComite.setActivo(false);
            comiteService.save(nuevoComite);
            operacionService.guardarOperacion("Insert", enlaceSession.getId(),"Sin datos previos", integrante.toStringIntegrante());
            attributes.addFlashAttribute("mensaje", "Se ha creado correctamente");    
        }
        
        return "redirect:/comites/listar/"+coloniaID;
    }

    /**
     * Agrega nuevos integrantes a un comité existente
     */
    @PostMapping("/integrantes/existente/agregar")
    public String addExisting(Model model,Integrante integrante,  BindingResult result, RedirectAttributes attributes, HttpSession httpSession){
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        if (result.hasErrors()){
            List<String> errores = new ArrayList<>();
            for (ObjectError error:result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        }else {

            integrante.setComite(comite);
            integrante.setEnabled(true);

            if(integrante.getTipoUsuario() == null ){
                integrante.setPresidente(false);
                integrante.setTipoUsuario("Integrante");
            }

            String encode = passwordEncoder.encode(integrante.getPassword());
            integrante.setPassword(encode);
            Rol rolUsuario = rolService.getOne(3);
            Set<Rol> roles = new HashSet<>();
            roles.add(rolUsuario);
            integrante.setRoles(roles);

            integrantes.add(integrante);
            
            comite.setIntegrantes(integrantes);

            if(comite.getIntegrantes().size()>=3){
                comite.setActivo(true);
            }else{
                comite.setActivo(false);
            }
            comiteService.save(comite);
            operacionService.guardarOperacion("Insert", enlaceSession.getId(),"Sin datos previos", integrante.toStringIntegrante()); 
            attributes.addFlashAttribute("mensaje", "Se ha creado correctamente");   
        }

        return "redirect:/comites/integrantes/listar/"+comite.getId();
    }

    @GetMapping("/integrantes/existente/quitar/{telefono}")
    public String deleteExisting(Model model, @PathVariable("telefono") String telefono, HttpSession httpSession){
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        Integrante anterior = (Integrante) usuarioService.findByTelefono(telefono);
        usuarioService.deleteByTelefono(telefono);
        operacionService.guardarOperacion("Delete", enlaceSession.getId(), anterior.toStringIntegrante(),"Sin datos previos"); 
        return "redirect:/comites/integrantes/listar/"+comite.getId();
    }

    @PostMapping("/integrantes/existente/actualizar")
    public String updateExisting(@ModelAttribute("integrante")Integrante integrante, HttpSession httpSession){
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        Integrante anterior = integranteService.getOne(integrante.getId());

        integrantes.removeIf(i -> i.getId().equals(integrante.getId()));
        integrante.setEnabled(true);
        if(integrante.getTipoUsuario().equals("Presidente")){
            integrante.setPresidente(true);
        }else{
            integrante.setPresidente(false);
        }
        integrante.setComite(comite);
        integrantes.add(integrante);
        operacionService.guardarOperacion("Update", enlaceSession.getId(), anterior.toStringIntegrante(),integrante.toStringIntegrante()); 
        comiteService.save(comite);
        return "redirect:/comites/integrantes/listar/"+comite.getId();
    }

    @GetMapping("/integrantes/existente/detalles/{username}")
    public String getEntity(@PathVariable("username") String username,Model model){
        model.addAttribute("integrante",usuarioService.findByUsername(username));
        return "comite/actualizarintegrante";
    }

    @GetMapping("/guardar/{id}")
    public String save(@PathVariable("id") Integer id, HttpSession httpSession) {
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        Colonia colonia = coloniaService.getOne(coloniaID);
        comite.setIntegrantes(integrantes);
        comite.setColonia(colonia);
        comiteService.save(comite);
        operacionService.guardarOperacion("Insert", enlaceSession.getId(),"Sin datos previos", comite.toStringComite()); 
        return "redirect:/comites/listar/"+id;
    }

    @GetMapping("/actualizar")
    public String update(HttpSession httpSession) {
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        integranteService.deleteMultple(listIds);
        Colonia colonia = coloniaService.getOne(coloniaID);
        comite.setIntegrantes(integrantes);
        comite.setColonia(colonia);
        Comite anterior = comiteService.getOne(comite.getId());
        comiteService.save(comite); 
        operacionService.guardarOperacion("Update", enlaceSession.getId(),anterior.toStringComite(), comite.toStringComite());
        return "redirect:/comites/listar/"+coloniaID;
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable("id") Integer id, HttpSession httpSession) {
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        Comite anterior = comiteService.getOne(id);
        Comite actual = comiteService.getOne(id);
        for (Integrante integrante : actual.getIntegrantes()) {
            operacionService.guardarOperacion("Delete", enlaceSession.getId(), integrante.toStringIntegrante(),"Sin datos previos"); 
            usuarioService.delete(integrante.getId());
        }
        comiteService.delete(id);
        operacionService.guardarOperacion("Update", enlaceSession.getId(),anterior.toStringComite(), "Sin datos previos");
        return "redirect:/comites/listar/"+coloniaID;
    }
}
