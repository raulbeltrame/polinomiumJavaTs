package com.LuanGabriel.polinomium.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbDica")
public class Dica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String conteudo;

    @ManyToOne
    @JoinColumn(name="FK_Atividade")
    @JsonBackReference(value="atividade")
    Atividade atividade;

}
