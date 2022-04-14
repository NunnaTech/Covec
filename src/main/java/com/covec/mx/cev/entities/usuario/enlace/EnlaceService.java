package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnlaceService {
    @Autowired
    private EnlaceRepository repository;

    public Page<Enlace> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Enlace getOne(int id){
        Optional<Enlace> exist = repository.findById(id);
        if (exist.isPresent()){
            Enlace obj;
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public List<Enlace> getAllByMunicipio(Municipio municipio){
        return repository.findAllByMunicipioEquals(municipio);
    }

    public Enlace save(Enlace newObject){
        repository.save(newObject);
        return newObject;
    }

    public Enlace update(Enlace newObject){
        Optional<Enlace> exist = Optional.empty();
        Enlace enlace = null;
        exist = repository.findById(newObject.getId());
        if (!exist.isEmpty()){
            exist.get().setUsername(newObject.getUsername());
            exist.get().setNombreCompleto(newObject.getNombreCompleto());
            exist.get().setTelefono(newObject.getTelefono());
            repository.save(exist.get());
             enlace = exist.get();
        }

        return enlace;
    }

    public Enlace getByMunicipio(Municipio municipio){
        Enlace enlace = repository.getByMunicipioEquals(municipio);
        if (enlace != null){
            return enlace;
        }
        return null;
    }

    public void delete(int id){
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }
    }
}
