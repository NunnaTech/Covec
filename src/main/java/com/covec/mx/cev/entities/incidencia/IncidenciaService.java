package com.covec.mx.cev.entities.incidencia;

import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import com.covec.mx.cev.entities.usuario.integrante.Integrante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repository;

    public List<Incidencia> getAllIncidencias() {
        return repository.findAll();
    }

    public Page<Incidencia> getAllIncidenciasIntegrante(Integrante integrante, Pageable pageable) {
        return repository.getAllByIntegranteEquals(integrante, pageable);
    }

    public Incidencia getOne(int id) {
        Optional<Incidencia> exist = repository.findById(id);
        if (exist.isPresent()) {
            Incidencia obj = null;
            obj = exist.get();
            return obj;
        }
        return null;
    }

    public List<Incidencia> filtrar(List<Incidencia> incidenciaPage, Enlace enlace) {
        List<Incidencia> incidencias = new ArrayList<>();
        for (Incidencia incidencia : incidenciaPage) {
            for (Integrante integrante : incidencia.getIntegrante().getComite().getIntegrantes()) {
                if (integrante.getComite().getColonia().getMunicipio() == enlace.getMunicipio()) {
                    incidencias.add(incidencia);
                }
            }
        }
        return incidencias;
    }

    public Incidencia save(Incidencia newObject) {
        repository.save(newObject);
        return newObject;
    }

    public Incidencia update(Incidencia newObject) {
        Optional<Incidencia> exist;
        exist = repository.findById(newObject.getId());
        if (!exist.isEmpty()) {
            exist.get().setEstatus(newObject.getEstatus());
            repository.save(exist.get());
            return exist.get();
        }
        return null;
    }


    public void delete(int id) {
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }
    }
}
