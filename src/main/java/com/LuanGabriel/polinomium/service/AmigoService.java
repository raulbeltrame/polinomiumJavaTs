package com.LuanGabriel.polinomium.service;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Amigo;
import com.LuanGabriel.polinomium.repository.AmigoRepository;

import org.springframework.stereotype.Service;

@Service
public class AmigoService {
    
    private final AmigoRepository amigoRepository;

    public AmigoService(AmigoRepository amigoRepository){
        this.amigoRepository = amigoRepository;
    }

    public List<Amigo> findAllList(){
        return amigoRepository.findAll();
    }

    public Optional<Amigo> findOne(Long id){
        // log.debug("Request to get Amigo : {}", id);
        return amigoRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Amigo : {}", id);
        amigoRepository.deleteById(id);
    }

    public Amigo save(Amigo amigo){
        // log.debug("Request to save Amigo : {}", user);
        amigoRepository.save(amigo);
        return amigo;
    }


}
