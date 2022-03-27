package com.covec.mx.cev.entities.incidencia;

import java.util.List;

import com.covec.mx.cev.entities.usuario.integrante.Integrante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia,Integer> {
    List<Incidencia> getAllByIntegranteEquals(Integrante integrante);
}
