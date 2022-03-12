package com.covec.mx.cev.entities.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll(){
        return repository.findAll();
    }

    public Categoria getOne(int id){
        Optional<Categoria> exist = repository.findById(id);
        if (exist.isPresent()){
            Categoria categoria = new Categoria();
            categoria = exist.get();
            return categoria;
        }
        return null;
    }

    public Categoria save(Categoria categoria){
        repository.save(categoria);
        return categoria;
    }


    public Categoria update(Categoria newObject){
        Optional<Categoria> exist = Optional.empty();
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
