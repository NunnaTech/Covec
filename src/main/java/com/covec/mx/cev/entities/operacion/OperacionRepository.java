package com.covec.mx.cev.entities.operacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion,Integer> {
    
    @Procedure(procedureName = "operaciones")
    void guardarOPeracion(@Param("eaccion") String eaccion, @Param("eid_usuario")Integer id, @Param("eanterior") String eanterior, @Param("eactual") String eactual);
}
