package com.covec.mx.cev.entities.colonia;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia,Integer> {
    @Query (value = "SELECT * FROM colonias WHERE id_municipio =:id", nativeQuery = true)
    Page<Colonia> findAllByMunicipio(@Param("id") Integer id, Pageable pageable);
}
