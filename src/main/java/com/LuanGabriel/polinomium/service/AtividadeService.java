package com.LuanGabriel.polinomium.service;
import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Atividade;
import com.LuanGabriel.polinomium.repository.AtividadeRepository;

import org.springframework.stereotype.Service;

@Service
public class AtividadeService {
    
    private final AtividadeRepository atividadeRepository;

    public AtividadeService(AtividadeRepository atividadeRepository){
        this.atividadeRepository = atividadeRepository;
    }

    public List<Atividade> findAllList(){
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> findOne(Long id){
        // log.debug("Request to get Atividade : {}", id);
        return atividadeRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Atividade : {}", id);
        atividadeRepository.deleteById(id);
    }

    public Atividade save(Atividade ativ){
        // log.debug("Request to save Atividade : {}", user);
        atividadeRepository.save(ativ);
        return ativ;
    }

    public List<Atividade> encontrarPorLicao(Long id){

        return atividadeRepository.encontrarPorLicao(id);
    }


}
