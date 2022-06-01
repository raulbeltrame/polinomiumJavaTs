package com.LuanGabriel.polinomium.repository;

import com.LuanGabriel.polinomium.domain.Dica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DicaRepository extends JpaRepository<Dica, Long>  {
    
}
