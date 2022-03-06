package com.covec.mx.cev.entities.comite;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComiteRepository extends CrudRepository<Comite, Integer> {
    
}
