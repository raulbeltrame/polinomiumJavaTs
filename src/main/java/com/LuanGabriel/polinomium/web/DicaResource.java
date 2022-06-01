package com.LuanGabriel.polinomium.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Atividade;
import com.LuanGabriel.polinomium.domain.Dica;
import com.LuanGabriel.polinomium.service.AtividadeService;
import com.LuanGabriel.polinomium.service.DicaService;

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
@RequestMapping("/dicas")
public class DicaResource {
    
    private final DicaService dicaService;
    private final AtividadeService atividadeService;
    private final Logger log = LoggerFactory.getLogger(DicaResource.class);

    public DicaResource(DicaService dicaService, AtividadeService atividadeService){
        this.dicaService = dicaService;
        this.atividadeService = atividadeService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Dica> getDica(@PathVariable Long id){
        Optional<Dica> dica = dicaService.findOne(id);
        if(dica.isPresent()){
            return ResponseEntity.ok().body(dica.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value="/")
    public ResponseEntity<List<Dica>> getDicas() {
        List<Dica> dicas = dicaService.findAllList();
        if(dicas.size()>0){
            return ResponseEntity.ok().body(dicas);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Dica> createDica(
            @RequestBody Dica dica
    ) throws URISyntaxException {
        log.debug("REST request to save Dica: {}", dica);
        if (dica.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova dica não pode ter um ID");
        }
        // Atividade ativ = atividadeService.findOne(dica.getAtividade().getId()).get();
        // ativ.getDicas().add(dica);
        // atividadeService.save(ativ);
        // dica.setAtividade(ativ);
        Dica result = dicaService.save(dica);
        return ResponseEntity.created(new URI("/api/dicas/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDica(@PathVariable Long id){
        Optional<Dica> dica = dicaService.findOne(id);
        dicaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Dica> updateDica(@RequestBody Dica dica) throws URISyntaxException{
        if(dica.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid dica null id");
        }

        Dica dic = dicaService.save(dica);
        return ResponseEntity.ok().body(dic);
    }


}
