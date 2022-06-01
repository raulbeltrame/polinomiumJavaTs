package com.LuanGabriel.polinomium.service;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.MatriculaAtividade;
import com.LuanGabriel.polinomium.repository.MatriculaAtividadeRepository;

import org.springframework.stereotype.Service;

@Service
public class MatriculaAtividadeService {
    
    private final MatriculaAtividadeRepository matriculaAtividadeRepository;

    public MatriculaAtividadeService(MatriculaAtividadeRepository matriculaAtividadeRepository){
        this.matriculaAtividadeRepository = matriculaAtividadeRepository;
    }

    public List<MatriculaAtividade> findAllList(){
        return matriculaAtividadeRepository.listarMatriculasAtividade();
    }

    public Optional<MatriculaAtividade> findOne(Long id){
        // log.debug("Request to get Usuario : {}", id);
        return matriculaAtividadeRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Usuario : {}", id);
        matriculaAtividadeRepository.deleteById(id);
    }

    public MatriculaAtividade save(MatriculaAtividade matriculaAtividade){
        // log.debug("Request to save Usuario : {}", user);

        matriculaAtividadeRepository.save(matriculaAtividade);
        return matriculaAtividade;
    }

}
