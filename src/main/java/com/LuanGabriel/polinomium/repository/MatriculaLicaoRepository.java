package com.LuanGabriel.polinomium.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.LuanGabriel.polinomium.domain.Licao;
import com.LuanGabriel.polinomium.domain.MatriculaLicao;
import com.LuanGabriel.polinomium.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaLicaoRepository  extends JpaRepository<MatriculaLicao, Long>{
    
    Optional<MatriculaLicao> findById(Long id);

    @Query("select distinct m from MatriculaLicao m")
    List<MatriculaLicao> listarMatriculasLicao();

    @Query(value="SELECT * FROM tbmatricula_licao WHERE licao=:idLicao AND usuario=:idUsuario", nativeQuery = true)
    MatriculaLicao getByUsuario(@Param("idLicao") Long idLicao, @Param("idUsuario") Long idUsuario);

}
