package com.LuanGabriel.polinomium.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbUsuario")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length = 45)
    private String nome;
    @Column(nullable=false)
    private boolean userAtivo = true;
    @Column(nullable=false, length = 45)
    private String username;
    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private java.util.Date dataNascimento;
    @Column(nullable=false, length = 45)
    private String email;
    @Column(nullable=false, length = 45)
    @Getter
    @Setter
    private String senha;
    @Column(nullable=false)
    private int XP;
    @Column(nullable=false)
    private String[] ofensiva;
    @Column(nullable=false)
    private int nível;
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value="usuario")
    private List<MatriculaLicao> matriculaLicao;
    
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value="usuario")
    private List<MatriculaAtividade> matriculaAtividades;

    @ManyToMany 
    @JoinTable(name="associacao_usuario_amigo", 
        joinColumns = @JoinColumn(name="fk_usuario"), 
        inverseJoinColumns = @JoinColumn(name = "fk_amigo")) 
    private List<Amigo> amigos;

}

