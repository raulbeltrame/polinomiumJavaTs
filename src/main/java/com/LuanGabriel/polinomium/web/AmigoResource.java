package com.LuanGabriel.polinomium.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Amigo;
import com.LuanGabriel.polinomium.service.AmigoService;

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

@RestController
@RequestMapping("/amigos")
public class AmigoResource {
    
    private final AmigoService amigoService;
    private final Logger log = LoggerFactory.getLogger(AmigoResource.class);

    public AmigoResource(AmigoService amigoService){
        this.amigoService = amigoService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Amigo> getAmigo(@PathVariable Long id){
        Optional<Amigo> amigo = amigoService.findOne(id);
        if(amigo.isPresent()){
            return ResponseEntity.ok().body(amigo.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<Amigo>> getAmigos() {
        List<Amigo> amigos = amigoService.findAllList();
        if(amigos.size()>0){
            return ResponseEntity.ok().body(amigos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Amigo> createAmigo(
            @RequestBody Amigo amigo
    ) throws URISyntaxException {
        log.debug("REST request to save Amigo: {}", amigo);
        Amigo result = amigoService.save(amigo);
        return ResponseEntity.created(new URI("/api/amigos/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmigo(@PathVariable Long id){
        Optional<Amigo> amigo = amigoService.findOne(id);
        amigoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Amigo> updateAmigo(@RequestBody Amigo amigo) throws URISyntaxException{
        if(amigo.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amigo null id");
        }

        Amigo ativ = amigoService.save(amigo);
        return ResponseEntity.ok().body(ativ);
    }



}
