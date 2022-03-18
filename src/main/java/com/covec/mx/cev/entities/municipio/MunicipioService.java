package com.covec.mx.cev.entities.municipio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {
    @Autowired
    private MunicipioRepository repository;

    public List<Municipio> getAll(){
        return repository.findAll();
    }

    public Municipio getOne(int id){
        Optional<Municipio> exist = repository.findById(id);
        if (exist.isPresent()){
            Municipio obj = new Municipio();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Municipio save(Municipio newObject){
        repository.save(newObject);
        return newObject;
    }

    public Municipio update(Municipio newObject){
        Optional<Municipio> exist = Optional.empty();
        exist = repository.findById(newObject.getId());
        if (!exist.isEmpty())
            repository.save(newObject);
        return exist.get();
    }

    public void delete(int id){
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }
    }
}
