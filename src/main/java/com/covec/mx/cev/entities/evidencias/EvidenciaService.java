package com.covec.mx.cev.entities.evidencias;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvidenciaService {
    @Autowired
    private EvidenciaRepository repository;

    public List<Evidencia> getAllEvidencias(Incidencia incidencia){
        return repository.getAllByIncidenciaEquals(incidencia);
    }
}
