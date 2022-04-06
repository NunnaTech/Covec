package com.covec.mx.cev.entities.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol getOne(int id){
        return rolRepository.getById(id);
    }
}
