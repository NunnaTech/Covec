package com.covec.mx.cev.entities.usuario.integrante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntegranteService {
    @Autowired
    private IntegranteRepository integranteRepository;

    public void deleteMultple(List<Integer> listaIds){
        integranteRepository.deleteAllById(listaIds);
    }   
    public Integrante getOne(int id){
        Optional<Integrante> exist = integranteRepository.findById(id);
        if (exist.isPresent()){
            Integrante obj = new Integrante();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public void deleteById(Integer id){
        integranteRepository.deleteById(id);
    }
}
