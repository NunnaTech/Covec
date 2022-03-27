package com.covec.mx.cev.entities.municipio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio,Integer> {
    @Query(value = "SELECT * FROM municipios M LEFT JOIN enlace E ON M.id_municipio = E.id_municipio WHERE E.id_usuario IS NULL", nativeQuery = true)
    List<Municipio> obtenerDisponibles();

}
