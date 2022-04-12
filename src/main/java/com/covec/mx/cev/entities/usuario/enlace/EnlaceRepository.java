package com.covec.mx.cev.entities.usuario.enlace;

import com.covec.mx.cev.entities.municipio.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnlaceRepository extends JpaRepository<Enlace,Integer> {
    Enlace getByMunicipioEquals(Municipio municipio);
    List<Enlace> findAllByMunicipioEquals(Municipio municipio);
}
