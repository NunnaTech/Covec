package com.covec.mx.cev.entities.incidencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repository;

    public List<Incidencia> getAll(){
        return repository.findAll();
    }

    public Incidencia getOne(int id){
        Optional<Incidencia> exist = repository.findById(id);
        if (exist.isPresent()){
            Incidencia obj = new Incidencia();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Incidencia save(Incidencia newObject){
        repository.save(newObject);
        return newObject;
    }

    public Incidencia update(Incidencia newObject){
        Optional<Incidencia> exist = Optional.empty();
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
