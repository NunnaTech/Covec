package com.covec.mx.cev.entities.usuario.integrante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante,Integer>{
    
}
