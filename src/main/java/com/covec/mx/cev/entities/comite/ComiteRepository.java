package com.covec.mx.cev.entities.comite;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComiteRepository extends JpaRepository<Comite, Integer> {
    @Query(value = "SELECT * FROM comites WHERE id_colonia = :id", nativeQuery = true)
    Page<Comite> getAllById(@Param("id") Integer id, Pageable pageable);

    @Query(value = "SELECT * FROM comites WHERE id_colonia = :id", nativeQuery = true)
    Optional<Comite> getByColony(@Param("id") int id);
}