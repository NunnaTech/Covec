package com.covec.mx.cev.entities.sesion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends CrudRepository<Sesion,Integer>{
    
}
