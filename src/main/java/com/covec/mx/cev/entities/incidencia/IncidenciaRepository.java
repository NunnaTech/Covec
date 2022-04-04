package com.covec.mx.cev.entities.incidencia;

import com.covec.mx.cev.entities.usuario.integrante.Integrante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia,Integer> {
    Page<Incidencia> getAllByIntegranteEquals(Integrante integrante, Pageable pageable);
}
