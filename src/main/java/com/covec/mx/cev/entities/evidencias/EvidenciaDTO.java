package com.covec.mx.cev.entities.evidencias;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EvidenciaDTO {
    private List<String> links;

    public EvidenciaDTO(List<String> links) {
        this.links = links;
    }
}
