package com.LuanGabriel.polinomium.web;


import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Atividade;
import com.LuanGabriel.polinomium.domain.MatriculaAtividade;
import com.LuanGabriel.polinomium.domain.MatriculaLicao;
import com.LuanGabriel.polinomium.domain.Usuario;
import com.LuanGabriel.polinomium.service.AtividadeService;
import com.LuanGabriel.polinomium.service.MatriculaAtividadeService;
import com.LuanGabriel.polinomium.service.MatriculaLicaoService;
import com.LuanGabriel.polinomium.service.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/matriculaAtividades")
public class MatriculaAtividadeResource {
    
    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);
    private final MatriculaAtividadeService matriculaAtividadeService;
    private final MatriculaLicaoService matriculaLicaoService;
    private final UsuarioService usuarioService;
    private final AtividadeService atividadeService;

    public MatriculaAtividadeResource(MatriculaAtividadeService matriculaAtividadeService, UsuarioService usuarioService, AtividadeService atividadeService, MatriculaLicaoService matriculaLicaoService){
        this.matriculaAtividadeService = matriculaAtividadeService;
        this.matriculaLicaoService = matriculaLicaoService;
        this.usuarioService = usuarioService;
        this.atividadeService = atividadeService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<MatriculaAtividade> getMatriculaAtividade(@PathVariable Long id){
        Optional<MatriculaAtividade> matricula = matriculaAtividadeService.findOne(id);
        if(matricula.isPresent()){
            return ResponseEntity.ok().body(matricula.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<MatriculaAtividade>> getMatriculasAtividade() {
        List<MatriculaAtividade> matriculasAtividade = matriculaAtividadeService.findAllList();
        if(matriculasAtividade.size()>0){
            return ResponseEntity.ok().body(matriculasAtividade);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatriculaAtividade(@PathVariable Long id){
        Optional<MatriculaAtividade> matriculaAtividade = matriculaAtividadeService.findOne(id);
        matriculaAtividadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<MatriculaAtividade> updateMatriculaAtividade(@RequestBody MatriculaAtividade matriculaAtividade) throws URISyntaxException{
        if(matriculaAtividade.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid matriculaAtividadee null id");
        }

        MatriculaAtividade matriculaAtiv = matriculaAtividadeService.save(matriculaAtividade);
        return ResponseEntity.ok().body(matriculaAtiv);
    }


    @PostMapping("/")
    public ResponseEntity<MatriculaAtividade> createMatriculaAtividade(
            @RequestBody MatriculaAtividade matriculaAtividade
    ) throws URISyntaxException {
        log.debug("REST request to save MatriculaAtividade: {}", matriculaAtividade);
        if (matriculaAtividade.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova matrícula não pode ter um ID");
        }
       //tenho que adicionar a matricula em lição e usuario
        Atividade atividade = atividadeService.findOne(
            matriculaAtividade.getAtividade().getId()
        ).get();

        atividade.getMatriculaAtividades().add(matriculaAtividade);
        atividadeService.save(atividade);

        Usuario user = usuarioService.findOne(
            matriculaAtividade.getUsuario().getId()
        ).get();
        
        user.getMatriculaAtividades().add(matriculaAtividade);
        usuarioService.save(user);


        MatriculaAtividade result = matriculaAtividadeService.save(matriculaAtividade);
        return ResponseEntity.created(new URI("/api/matriculasAtividade/" + result.getId()))
                .body(result);
    }


}
