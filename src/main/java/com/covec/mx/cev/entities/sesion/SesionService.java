package com.covec.mx.cev.entities.sesion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionService {
    @Autowired
    private SesionRepostory sesionRepostory;

    public void guardarSesion (Integer id){
        sesionRepostory.guardarSesion(id);
    }
}
