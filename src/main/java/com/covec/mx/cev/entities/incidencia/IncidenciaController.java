package com.covec.mx.cev.entities.incidencia;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.covec.mx.cev.entities.comentario.Comentario;
import com.covec.mx.cev.entities.comentario.ComentarioService;
import com.covec.mx.cev.entities.evidencias.EvidenciaService;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import com.covec.mx.cev.entities.usuario.enlace.EnlaceService;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import com.covec.mx.cev.entities.usuario.integrante.IntegranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidencias")
public class IncidenciaController {
    @Autowired
    private IncidenciaService service;
    @Autowired
    private EvidenciaService evidenciaService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private EnlaceService enlaceService;
    @Autowired
    private IntegranteService integranteService;

    @GetMapping("/all/{idenlace}")
    public String allIncidencias(@RequestParam Map<String, Object> params, @PathVariable("idenlace") Integer idEnlace,
            Model model) {
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        Enlace enlace = enlaceService.getOne(idEnlace);
        List<Incidencia> incidencias = service.filtrar(service.getAllIncidencias(), enlace);
        Pageable paging = PageRequest.of(page, 5);
        int inicioPag = Math.min((int) paging.getOffset(), incidencias.size());
        int finalPag = Math.min((inicioPag + paging.getPageSize()), incidencias.size());
        Page<Incidencia> pageIncidenciaFinal = new PageImpl<>(incidencias.subList(inicioPag, finalPag), paging,
                incidencias.size());
        int totalPages = pageIncidenciaFinal.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        model.addAttribute("incidencia", new Incidencia());
        model.addAttribute("enlaceUsuario", enlace);
        model.addAttribute("incidencias", pageIncidenciaFinal.getContent());
        return "incidencia/incidenciascrud";
    }


    @GetMapping("/allIntegrante/{idIntegrante}")
    public String allIncidenciasPresidente(@RequestParam Map<String, Object> params, @PathVariable("idIntegrante") Integer idIntegrante,
            Model model) {
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        Integrante integrante = integranteService.getOne(idIntegrante);
        List<Incidencia> incidencias = service.getAllIncidenciasIntegrante(integrante);
        Pageable paging = PageRequest.of(page, 5);
        int inicioPag = Math.min((int) paging.getOffset(), incidencias.size());
        int finalPag = Math.min((inicioPag + paging.getPageSize()), incidencias.size());
        Page<Incidencia> pageIncidenciaFinal = new PageImpl<>(incidencias.subList(inicioPag, finalPag), paging,
                incidencias.size());
        int totalPages = pageIncidenciaFinal.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        model.addAttribute("incidencia", new Incidencia());
        model.addAttribute("integranteUsuario", integrante);
        model.addAttribute("incidencias", pageIncidenciaFinal.getContent());
        return "incidencia/incidenciascrud";
    }



    @GetMapping("/getOne/{id}/{idenlace}")
    public String obtenerIncidencia(@PathVariable("id") Integer id, @PathVariable("idenlace") Integer idEnlace,
            Model model) {
        model.addAttribute("incidencia", service.getOne(id));
        model.addAttribute("evidencias", evidenciaService.getAllEvidencias(service.getOne(id)));
        model.addAttribute("enlace", enlaceService.getOne(idEnlace));
        model.addAttribute("comentarioIncidencia", new Comentario());
        model.addAttribute("comentarios",
                comentarioService.getAllChat(service.getOne(id), enlaceService.getOne(idEnlace)));
        return "incidencia/IncidenciaDetalle";
    }

    @PostMapping("/update")
    public String updateStatus(@ModelAttribute("categoria") Incidencia incidencia,
            @RequestParam("idEnlace") Integer idEnlace) {
        int idUrl = enlaceService.getOne(idEnlace).getId();
        service.update(incidencia);
        return "redirect:/incidencias/all/" + idUrl;
    }

    @PostMapping("/comentar")
    public String uploadMessage(@ModelAttribute("comentarioIncidencia") Comentario comentario) {
        comentarioService.save(comentario);
        return "redirect:/incidencias/getOne/" + comentario.getIncidencia().getId() + "/"
                + comentario.getEnlace().getId();
    }
}
