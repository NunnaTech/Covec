package com.covec.mx.cev.entities.usuario.enlace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnlaceRepository extends JpaRepository<Enlace,Integer> {
}
