package com.LuanGabriel.polinomium.repository;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.MatriculaAtividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaAtividadeRepository extends JpaRepository<MatriculaAtividade, Long>{
    
    Optional<MatriculaAtividade> findById(Long id);

    @Query("select distinct m from MatriculaAtividade m")
    List<MatriculaAtividade> listarMatriculasAtividade();

}
