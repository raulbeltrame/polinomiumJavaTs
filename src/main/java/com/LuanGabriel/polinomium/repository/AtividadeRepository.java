package com.LuanGabriel.polinomium.repository;


import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository  extends JpaRepository<Atividade, Long>{
    
    Optional<Atividade> findById(Long id);

    @Query("select distinct a from Atividade a")
    List<Atividade> listarAtividades();

    @Query(value="SELECT * FROM table_atividade WHERE licao=:idLicao", nativeQuery = true)
    List<Atividade> encontrarPorLicao(@Param("idLicao") Long idLicao);

}
