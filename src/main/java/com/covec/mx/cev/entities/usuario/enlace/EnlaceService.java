package com.covec.mx.cev.entities.usuario.enlace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnlaceService {
    @Autowired
    private EnlaceRepository repository;

    public List<Enlace> getAll(){
        return repository.findAll();
    }

    public Enlace getOne(int id){
        Optional<Enlace> exist = repository.findById(id);
        if (exist.isPresent()){
            Enlace obj = new Enlace();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Enlace save(Enlace newObject){
        repository.save(newObject);
        return newObject;
    }

    public Enlace update(Enlace newObject){
        Optional<Enlace> exist = Optional.empty();
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
