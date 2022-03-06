package com.covec.mx.cev.entities.operacion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends CrudRepository<Operacion,Integer> {
    
}
