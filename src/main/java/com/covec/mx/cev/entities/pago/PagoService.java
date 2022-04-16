package com.covec.mx.cev.entities.pago;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public Page<Pago> getAllByIntegrante(Collection<Incidencia> incidencias, Pageable pageable){
        return pagoRepository.findByIncidenciaIn(incidencias, pageable);
    }

    public Pago save(Pago pago){
        return pagoRepository.save(pago);
    }

    public List<Pago> getAllPagos(){
        return pagoRepository.findByOrderByIdDesc();
    }
}
