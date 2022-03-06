package com.covec.mx.cev.entities.municipio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends CrudRepository<Municipio,Integer> {
    
}
