package com.LuanGabriel.polinomium.service;

import com.LuanGabriel.polinomium.domain.MatriculaLicao;
import com.LuanGabriel.polinomium.repository.MatriculaLicaoRepository;


import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Usuario;
import com.LuanGabriel.polinomium.repository.UsuarioRepository;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

@Service
public class MatriculaLicaoService {
    
    private final MatriculaLicaoRepository matriculaLicaoRepository;

    public MatriculaLicaoService(MatriculaLicaoRepository matriculaLicaoRepository){
        this.matriculaLicaoRepository = matriculaLicaoRepository;
    }

    public List<MatriculaLicao> findAllList(){
        return matriculaLicaoRepository.listarMatriculasLicao();
    }

    public Optional<MatriculaLicao> findOne(Long id){
        // log.debug("Request to get Usuario : {}", id);
        return matriculaLicaoRepository.findById(id);
    }

    public void delete(Long id){
        // log.debug("Request to delete Usuario : {}", id);
        matriculaLicaoRepository.deleteById(id);
    }

    public MatriculaLicao save(MatriculaLicao matriculaLicao){
        // log.debug("Request to save Usuario : {}", user);

        matriculaLicaoRepository.save(matriculaLicao);
        return matriculaLicao;
    }

    public MatriculaLicao getByUsuario(Long idLicao, Long idUsuario){
        return matriculaLicaoRepository.getByUsuario(idLicao,idUsuario);
    }

}
