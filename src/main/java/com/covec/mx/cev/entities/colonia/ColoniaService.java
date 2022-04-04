package com.covec.mx.cev.entities.colonia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColoniaService {

    @Autowired
    private ColoniaRepository repository;

    public Page<Colonia> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Colonia getOne(int id){
        Optional<Colonia> exist = repository.findById(id);
        if (exist.isPresent()){
            Colonia obj = new Colonia();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Colonia save(Colonia newObject){
        repository.save(newObject);
        return newObject;
    }

    public Colonia update(Colonia newObject){
        Optional<Colonia> exist = Optional.empty();
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
