package com.covec.mx.cev.entities.comite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ComiteService {
    @Autowired
    private ComiteRepository repository;

    public Page<Comite> getAll(Integer id,Pageable pageable){
        return  repository.getAllById(id, pageable);
    }

    public Comite getOne(int id){
        Optional<Comite> exist = repository.findById(id);
        if (exist.isPresent()){
            Comite obj = new Comite();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Comite getByColony(int id){
        Optional<Comite> exist = repository.getByColony(id);
        if (exist.isPresent()){
            Comite obj = new Comite();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Comite save(Comite newObject){
        repository.save(newObject);
        return newObject;
    }

    public Comite update(Comite newObject){
        Optional<Comite> exist = Optional.empty();
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
