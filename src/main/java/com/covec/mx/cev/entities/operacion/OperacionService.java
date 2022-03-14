package com.covec.mx.cev.entities.operacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperacionService {
    @Autowired
    private OperacionRepository repository;

    public List<Operacion> getAll(){
        return repository.findAll();
    }

    public Operacion getOne(int id){
        Optional<Operacion> exist = repository.findById(id);
        if (exist.isPresent()){
            Operacion obj = new Operacion();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Operacion save(Operacion newObject){
        repository.save(newObject);
        return newObject;
    }

    public Operacion update(Operacion newObject){
        Optional<Operacion> exist = Optional.empty();
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
