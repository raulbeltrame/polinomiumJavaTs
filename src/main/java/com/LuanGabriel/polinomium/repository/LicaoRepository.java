package com.LuanGabriel.polinomium.repository;

import com.LuanGabriel.polinomium.domain.Licao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicaoRepository extends JpaRepository<Licao, Long>{
    
}
