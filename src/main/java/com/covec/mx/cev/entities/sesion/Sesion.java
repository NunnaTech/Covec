package com.covec.mx.cev.entities.sesion;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Sesiones")
@NamedStoredProcedureQuery(name = "sesiones",procedureName = "sesiones", parameters = {
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "eid_usuario", type = Integer.class)
})
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesiones")
    private Integer id;
    @Column(name = "fecha_sesion")
    private LocalDateTime fechaSesion;
}
