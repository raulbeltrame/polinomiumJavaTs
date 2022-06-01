package com.LuanGabriel.polinomium.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Atividade;
import com.LuanGabriel.polinomium.service.AtividadeService;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/atividades")
public class AtividadeResource {
    
    private final AtividadeService atividadeService;
    private final Logger log = LoggerFactory.getLogger(AtividadeResource.class);

    public AtividadeResource(AtividadeService atividadeService){
        this.atividadeService = atividadeService;
    }

//    @GetMapping(value="/criar/{nome}")
//    public String helloApp(@PathVariable String nome) {
//        Atividade atividade = new Atividade();
//        atividade.setNome(nome);
//        atividadeService.save(atividade);
//        return atividade.getNome() + " criado!";
//    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Atividade> getAtividade(@PathVariable Long id){
        Optional<Atividade> atividade = atividadeService.findOne(id);
        if(atividade.isPresent()){
            return ResponseEntity.ok().body(atividade.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/getByLicao/{id}")
    public ResponseEntity<List<Atividade>> encontrarPorLicao(@PathVariable Long id){
        // System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        // System.out.println(id);
        // System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<Atividade> atividades = atividadeService.encontrarPorLicao(id);
        if(atividades!=null){
            return ResponseEntity.ok().body(atividades);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<Atividade>> getAtividades() {
        List<Atividade> atividades = atividadeService.findAllList();
        if(atividades.size()>0){
            return ResponseEntity.ok().body(atividades);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Atividade> createAtividade(
            @RequestBody Atividade atividade
    ) throws URISyntaxException {
        log.debug("REST request to save Atividade: {}", atividade);
        if (atividade.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova atividade não pode ter um ID");
        }
        Atividade result = atividadeService.save(atividade);
        return ResponseEntity.created(new URI("/api/atividades/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long id){
        Optional<Atividade> atividade = atividadeService.findOne(id);
        atividadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Atividade> updateAtividade(@RequestBody Atividade atividade) throws URISyntaxException{
        if(atividade.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid atividade null id");
        }

        Atividade ativ = atividadeService.save(atividade);
        return ResponseEntity.ok().body(ativ);
    }




}
