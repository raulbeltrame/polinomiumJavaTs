package com.LuanGabriel.polinomium.repository;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Amigo;
import com.LuanGabriel.polinomium.domain.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AmigoRepository extends JpaRepository<Amigo, Long>{
    
    Optional<Amigo> findById(Long id);

    @Query("select distinct a from Amigo a")
    List<Amigo> listarAmigos();

}
