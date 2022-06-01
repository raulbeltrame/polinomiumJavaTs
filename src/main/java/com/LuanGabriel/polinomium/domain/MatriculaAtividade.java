package com.LuanGabriel.polinomium.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbmatriculaAtividade")
public class MatriculaAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Boolean isLocked;

    @Column(nullable=false)
    private Boolean isFinished;

    @Column(nullable=false)
    private Boolean Acerto;

    @ManyToOne
    @JoinColumn(name="FK_Usuario")
    @JsonBackReference(value="usuario")
    @JsonProperty("usuario")
    Usuario usuario;

    @ManyToOne
    @JoinColumn(name="FK_Atividade")
    @JsonBackReference(value="atividade")
    @JsonProperty("atividade")
    Atividade atividade;

}
 