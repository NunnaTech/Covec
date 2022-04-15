package com.covec.mx.cev.entities.pago;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface PagoRepository extends JpaRepository<Pago,Integer> {

    Page<Pago> findByIncidenciaIn(Collection<Incidencia> incidencias, Pageable pageable);
}
