package com.covec.mx.cev.entities.incidencia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.covec.mx.cev.entities.categoria.CategoriaService;
import com.covec.mx.cev.entities.comentario.Comentario;
import com.covec.mx.cev.entities.comentario.ComentarioService;
import com.covec.mx.cev.entities.evidencias.Evidencia;
import com.covec.mx.cev.entities.evidencias.EvidenciaDTO;
import com.covec.mx.cev.entities.evidencias.EvidenciaService;
import com.covec.mx.cev.entities.operacion.OperacionService;
import com.covec.mx.cev.entities.pago.Pago;
import com.covec.mx.cev.entities.pago.PagoService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PagoService pagoService;
    @Autowired
    private OperacionService operacionService;

    @GetMapping("/all")
    public String allIncidencias(@RequestParam Map<String, Object> params, HttpSession httpSession, Model model) {
        Enlace enlaceSession = (Enlace) httpSession.getAttribute("user");
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        Enlace enlace = enlaceService.getOne(enlaceSession.getId());
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

    @GetMapping("/allPresidente")
    public String getAllPresidente(@RequestParam Map<String, Object> params, HttpSession httpSession, Model model) {
        Integrante integranteSession = (Integrante) httpSession.getAttribute("user");
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 5);
        Integrante integrante = integranteService.getOne(integranteSession.getId());
        Page<Incidencia> pageObject = service.getAllIncidenciasIntegrante(integrante, pageRequest);
        int totalPages = pageObject.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        model.addAttribute("incidenciasPresidente", pageObject.getContent());
        model.addAttribute("categorias", categoriaService.getAllList());
        model.addAttribute("incidencia", new Incidencia());
        model.addAttribute("evidenciaDTO", new EvidenciaDTO(new ArrayList<>()));
        model.addAttribute("integranteUsuario", integrante);
        return "incidencia/PresidenteCrud";
    }

    @GetMapping("/allPagos")
    public String getAllpagos(@RequestParam Map<String, Object> params, HttpSession httpSession, Model model) {
        Integrante session = (Integrante) httpSession.getAttribute("user");
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        PageRequest pageRequest = PageRequest.of(page, 5);
        Integrante integrante = integranteService.getOne(session.getId());
        List<Incidencia> incidencias = service.getAllIntegrante(integrante);
        Page<Pago> pageObject = pagoService.getAllByIntegrante(incidencias, pageRequest);
        int totalPages = pageObject.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("paginas", pages);
        }
        model.addAttribute("pagos", pageObject.getContent());
        return "incidencia/PagosIntegrante";
    }

    @GetMapping("/getOne/{id}/{idenlace}")
    public String obtenerIncidencia(@PathVariable("id") Integer id, @PathVariable(name = "idenlace") Integer idEnlace,
            Model model) {
        Enlace enlace = enlaceService.getOne(idEnlace);
        Incidencia incidencia = service.getOne(id);
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("evidencias", evidenciaService.getAllEvidencias(service.getOne(id)));
        model.addAttribute("enlace", enlace);
        model.addAttribute("comentarioIncidencia", new Comentario());
        List<Comentario> comentarios = comentarioService.getAll();
        List<Comentario> filtrados = new ArrayList<>();
        for (Comentario c : comentarios) {
            if (c.getIncidencia().getIntegrante().getComite().getColonia().getMunicipio().getId() == enlace
                    .getMunicipio().getId() && c.getIncidencia().getId() == incidencia.getId()) {
                filtrados.add(c);
            }
        }
        model.addAttribute("comentarios", filtrados);
        return "incidencia/IncidenciaDetalle";

    }

    @GetMapping("/getDetail/{id}")
    public String obtenerIncidenciaPresidente(@PathVariable("id") Integer id, Model model) {
        List<Comentario> comentarios = comentarioService.getAllByIncidencia(service.getOne(id));
        Incidencia incidencia = service.getOne(id);
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("pago", new Pago());
        model.addAttribute("evidencias", evidenciaService.getAllEvidencias(service.getOne(id)));
        model.addAttribute("comentarioPresidente", new Comentario());
        model.addAttribute("comentarios", comentarios);
        return "incidencia/PresidenteDetalle";
    }

    @PostMapping("/update")
    public String updateStatus(@ModelAttribute("categoria") Incidencia incidencia,
            @RequestParam("idEnlace") Integer idEnlace, HttpSession httpSession) {
        Enlace session = (Enlace) httpSession.getAttribute("user");
        Incidencia anterior = service.getOne(incidencia.getId());
        service.update(incidencia);
        operacionService.guardarOperacion("Update", session.getId(), anterior.toStringIncidencia(), incidencia.toStringIncidencia());
        return "redirect:/incidencias/all";
    }

    @PostMapping("/cobrar/{idIncidencia}/{idEnlace}")
    public String cobrarIncidencia(@PathParam("monto") double monto,
            @PathVariable("idIncidencia") Integer idIncidencia,
            @PathVariable("idEnlace") Integer idEnlace, RedirectAttributes attributes) {
        Incidencia cobrar = service.getOne(idIncidencia);
        cobrar.setMonto(monto);
        cobrar.setPagar(true);
        service.save(cobrar);
        attributes.addFlashAttribute("mensaje", "El cobro se realizo exitosamente");
        return "redirect:/incidencias/getOne/" + cobrar.getId() + "/" + idEnlace;
    }

    @PostMapping("/uploadEvidencia/{idPresidente}")
    public String uploadEvidencia(@Valid @ModelAttribute("incidenciasPresidente") Incidencia incidencia,
            BindingResult result, RedirectAttributes attributes,
            @ModelAttribute EvidenciaDTO evidenciaDTO, @PathVariable("idPresidente") int id, HttpSession httpSession) {

        Integrante session = (Integrante) httpSession.getAttribute("user");
        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            attributes.addFlashAttribute("errores", errores);
        } else {
            Date date = Date.valueOf(LocalDate.now());
            incidencia.setFechaRegistro(date);
            service.save(incidencia);
            operacionService.guardarOperacion("Insert", session.getId(), "Sin datos previos", incidencia.toStringIncidencia());
            for (String link : evidenciaDTO.getLinks()) {
                Evidencia evidencia = new Evidencia();
                evidencia.setEvidencia(link);
                evidencia.setIncidencia(incidencia);
                evidenciaService.save(evidencia);
            }
            attributes.addFlashAttribute("mensaje", "Se ha registrado correctamente");
        }
        return "redirect:/incidencias/allPresidente";
    }

    @PostMapping("/comentar")
    public String uploadMessage(@ModelAttribute("comentarioIncidencia") Comentario comentario) {
        comentarioService.save(comentario);
        return "redirect:/incidencias/getOne/" + comentario.getIncidencia().getId() + "/"
                + comentario.getEnlace().getId();
    }

    @PostMapping("/pagar/{idIncidencia}")
    public String pagarIncidencia(@PathVariable("idIncidencia") Integer idIncidencia,
            @ModelAttribute("pago") Pago pago) {
        pago.setFecha(LocalDate.now().toString());
        pagoService.save(pago);
        Incidencia incidencia = service.getOne(idIncidencia);
        incidencia.setMonto(0.0);
        incidencia.setEstatus("Atendida");
        service.save(incidencia);
        return "redirect:/incidencias/getDetail/" + idIncidencia;
    }

    @PostMapping("/comentarPresidente")
    public String uploadMessagePresidente(@ModelAttribute("comentarioPresidente") Comentario comentario) {
        comentarioService.save(comentario);
        return "redirect:/incidencias/getDetail/" + comentario.getIncidencia().getId();
    }
}
