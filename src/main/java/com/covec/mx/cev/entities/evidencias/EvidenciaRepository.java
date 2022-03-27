package com.covec.mx.cev.entities.evidencias;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenciaRepository extends JpaRepository<Evidencia,Integer> {
    List<Evidencia> getAllByIncidenciaEquals(Incidencia incidencia);
}
