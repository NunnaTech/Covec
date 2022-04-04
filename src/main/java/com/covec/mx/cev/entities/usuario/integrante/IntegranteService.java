package com.covec.mx.cev.entities.usuario.integrante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegranteService {
    @Autowired
    private IntegranteRepository integranteRepository;

    public void deleteMultple(List<Integer> listaIds){
        integranteRepository.deleteAllById(listaIds);
    }   
}
