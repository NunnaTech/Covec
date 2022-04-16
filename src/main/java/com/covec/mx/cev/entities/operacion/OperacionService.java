package com.covec.mx.cev.entities.operacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OperacionService {
    @Autowired
    private OperacionRepository repository;

    public void guardarOperacion(String eaccion, Integer id, String eanterior, String eactual){
        repository.guardarOPeracion(eaccion, id, eanterior, eactual);
    }

    public List<Operacion> getAllOperaciones (){
        return repository.findByOrderByIdDesc();
    }
}
