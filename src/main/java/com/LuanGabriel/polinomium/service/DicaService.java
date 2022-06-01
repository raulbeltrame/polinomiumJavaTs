package com.LuanGabriel.polinomium.service;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Dica;
import com.LuanGabriel.polinomium.repository.DicaRepository;

import org.springframework.stereotype.Service;

@Service
public class DicaService {
    
    private final DicaRepository dicaRepository;

    public DicaService(DicaRepository dicaRepository){
        this.dicaRepository = dicaRepository;
    }

    public List<Dica> findAllList(){
        return dicaRepository.findAll();
    }

    public Optional<Dica> findOne(Long id){
        // log.debug("Request to get Dica : {}", id);
        return dicaRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Dica : {}", id);
        dicaRepository.deleteById(id);
    }

    public Dica save(Dica dica){
        // log.debug("Request to save Dica : {}", user);
        dicaRepository.save(dica);
        return dica;
    }


}
