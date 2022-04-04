package com.covec.mx.cev.entities.comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository <Comentario,Integer>{
}
