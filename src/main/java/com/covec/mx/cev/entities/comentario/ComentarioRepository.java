package com.covec.mx.cev.entities.comentario;

import com.covec.mx.cev.entities.incidencia.Incidencia;
import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository <Comentario,Integer>{
    List<Comentario> getAllByIncidenciaEqualsAndEnlaceEquals(Incidencia incidencia, Enlace enlace);

    List<Comentario> getAllByIncidenciaEquals(Incidencia incidencia);
}
