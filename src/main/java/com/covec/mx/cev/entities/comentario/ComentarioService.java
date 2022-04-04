package com.covec.mx.cev.entities.comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository repository;

    public List<Comentario> getAll(){
        return repository.findAll();
    }

    public Comentario getOne(int id){
        Optional<Comentario> exist = repository.findById(id);
        if (exist.isPresent()){
            Comentario obj = new Comentario();
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public Comentario save(Comentario newObject){
        repository.save(newObject);
        return newObject;
    }

    public Comentario update(Comentario newObject){
        Optional<Comentario> exist = Optional.empty();
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
