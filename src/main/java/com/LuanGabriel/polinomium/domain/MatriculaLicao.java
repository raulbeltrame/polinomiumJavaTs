package com.LuanGabriel.polinomium.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbmatriculaLicao")
// @JsonIgnoreProperties({ "usuario", "licao" })
public class MatriculaLicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private Boolean isLocked;
    @Column(nullable=false)
    private Boolean isFinished;
    @ManyToOne
    @JsonBackReference(value="licao")
    @JsonProperty("licao")
    @JoinColumn(name="Licao")
    Licao licao;
    @ManyToOne
    @JsonBackReference(value="usuario")
    @JsonProperty("usuario")
    @JoinColumn(name="Usuario")
    Usuario usuario;

}
