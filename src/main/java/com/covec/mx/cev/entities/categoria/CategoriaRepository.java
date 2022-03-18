package com.covec.mx.cev.entities.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Categoria getByNombreIsLike(String nombre);
}
