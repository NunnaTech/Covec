package com.covec.mx.cev.entities.usuario.administrador;

import com.covec.mx.cev.entities.operacion.OperacionService;
import com.covec.mx.cev.entities.pago.PagoService;
import com.covec.mx.cev.entities.sesion.SesionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bitacora")
public class AdministradorController {
    
    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private SesionService sesionService;

    @Autowired
    private OperacionService operacionService;

    @GetMapping("/detalles")
    public String logs(Model model){
        model.addAttribute("pagos", pagoService.getAllPagos());
        model.addAttribute("sesiones",sesionService.getAllSesiones());
        model.addAttribute("operaciones",operacionService.getAllOperaciones());
        return "moderacion/logs";
    }
}
