package com.covec.mx.cev.entities.comentario;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
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


    public List<Comentario> getAllChatEnlace(Incidencia incidencia, Enlace enlace, Boolean value){
        return repository.getAllByIncidenciaEqualsAndEnlaceEqualsAndEsEnlaceEquals(incidencia, enlace, value);
    }


    public List<Comentario> getAllChat(Incidencia incidencia, Enlace enlace){
        return repository.getAllByIncidenciaEqualsAndEnlaceEquals(incidencia, enlace);
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


    public void delete(int id){
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }
    }
}
