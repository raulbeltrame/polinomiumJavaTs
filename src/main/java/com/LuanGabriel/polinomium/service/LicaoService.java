package com.LuanGabriel.polinomium.service;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Licao;
import com.LuanGabriel.polinomium.repository.LicaoRepository;

import org.springframework.stereotype.Service;

@Service
public class LicaoService {
    
    private final LicaoRepository licaoRepository;

    public LicaoService(LicaoRepository licaoRepository){
        this.licaoRepository = licaoRepository;
    }

    public List<Licao> findAllList(){
        return licaoRepository.findAll();
    }

    public Optional<Licao> findOne(Long id){
        // log.debug("Request to get Licao : {}", id);
        return licaoRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Licao : {}", id);
        licaoRepository.deleteById(id);
    }

    public Licao save(Licao licao){
        // log.debug("Request to save Licao : {}", user);
        licaoRepository.save(licao);
        return licao;
    }

}
