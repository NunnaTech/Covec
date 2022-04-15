package com.covec.mx.cev.entities.sesion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface SesionRepostory extends JpaRepository<Sesion,Integer>{
    @Procedure(procedureName = "sesiones")
    void guardarSesion(@Param("eid_usuario") Integer id);
}