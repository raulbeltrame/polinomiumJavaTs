package com.LuanGabriel.polinomium.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tableAtividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String enunciado;

    @Column(nullable = false, length = 150)
    private String conteudo;

    @Column(nullable = false, length = 45)
    private String solucao;

    @Column(nullable = false)
    private int dificuldade;

    @OneToMany(mappedBy = "atividade")
    @JsonManagedReference(value="atividade")
    List<Dica> dicas;
    
    @Column(nullable = false)
    String a;

    @Column(nullable = false)
    String b;

    @Column(nullable = false)
    String c;

    @Column(nullable = false)
    String d;

    @OneToMany(mappedBy = "atividade")
    @JsonManagedReference(value="atividade")
    List<MatriculaAtividade> matriculaAtividades;

    @ManyToOne
    @JsonBackReference(value="exercicios")
    @JoinColumn(name="Licao")
    Licao licao;

}
