package com.covec.mx.cev.entities.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Page<Categoria> getAll(Pageable pageable){
        return repository.findAll(pageable);
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
        if (!exist.isEmpty()){
            exist.get().setNombre(newObject.getNombre());
            repository.save(newObject);
        }
        return exist.get();
    }

    public void delete(int id){
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }
    }
}
