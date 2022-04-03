package com.covec.mx.cev.entities.municipio;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

import java.util.List;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

>>>>>>> TPF3
import java.util.Optional;

@Service
public class MunicipioService {
    @Autowired
<<<<<<< HEAD
    private MunicipioRepository repository;

    public List<Municipio> getAll(){
        return repository.findAll();
    }

    public List<Municipio> getAvailable(){
        return repository.obtenerDisponibles();
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
=======
    private MunicipioRepository municipioRepository;

    public Page<Municipio> getAll(Pageable pageable){
        return municipioRepository.findAll(pageable);
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
>>>>>>> TPF3
    }

    public Municipio update(Municipio newObject){
        Optional<Municipio> exist = Optional.empty();
<<<<<<< HEAD
        exist = repository.findById(newObject.getId());
        if (!exist.isEmpty())
            repository.save(newObject);
        return exist.get();
    }

    public void delete(int id){
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
=======
        exist = municipioRepository.findById(newObject.getId());
        if (!exist.isEmpty())
            municipioRepository.save(newObject);
        return exist.get();
    }
    public void delete(int id){
        boolean exist = municipioRepository.existsById(id);
        if (exist) {
            municipioRepository.deleteById(id);
>>>>>>> TPF3
        }
    }
}
