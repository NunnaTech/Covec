package com.covec.mx.cev.entities.municipio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {
    @Autowired
    private MunicipioRepository municipioRepository;

    public Page<Municipio> getAll(Pageable pageable){
        return municipioRepository.findAll(pageable);
    }

    public List<Municipio> getAvailable(){
        return municipioRepository.obtenerDisponibles();
    }

    public List<Municipio> getAllMunicipios(){
        return municipioRepository.findAll();
    }

    public Municipio getOne(int id){
        Optional<Municipio> exist = municipioRepository.findById(id);
        if (exist.isPresent()){
            Municipio municipio = new Municipio();
            municipio = exist.get();
            return municipio;
        }
        return null;
    }
    public Municipio save(Municipio municipio){
        municipioRepository.save(municipio);
        return municipio;
    }

    public Municipio update(Municipio newObject){
        Optional<Municipio> exist = Optional.empty();
        exist = municipioRepository.findById(newObject.getId());
        if (!exist.isEmpty())
            municipioRepository.save(newObject);
        return exist.get();
    }
    public void delete(int id){
        boolean exist = municipioRepository.existsById(id);
        if (exist) {
            municipioRepository.deleteById(id);
        }
    }
}
