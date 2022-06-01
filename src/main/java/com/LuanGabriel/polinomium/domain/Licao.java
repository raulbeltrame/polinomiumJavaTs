package com.LuanGabriel.polinomium.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbLicao")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//       property = "id")
public class Licao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false, length=1500)
    private String conteudo;

    @Column(nullable = false)
    private int recompensa; 
    
    @OneToMany(mappedBy = "licao")
    @JsonManagedReference(value="licao")
    private List<MatriculaLicao> matriculaLicao;
    
    @OneToMany(mappedBy = "licao")
    @JsonManagedReference(value="exercicios")
    private List<Atividade> exercicios;

    public Long getId() {
        return this.id;
    }
    
    

}
