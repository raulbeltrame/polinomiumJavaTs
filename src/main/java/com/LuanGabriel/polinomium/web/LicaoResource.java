package com.LuanGabriel.polinomium.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.LuanGabriel.polinomium.domain.Licao;
import com.LuanGabriel.polinomium.service.LicaoService;

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
@RequestMapping("/licoes")
public class LicaoResource {
    
    private final LicaoService licaoService;
    private final Logger log = LoggerFactory.getLogger(LicaoResource.class);

    public LicaoResource(LicaoService licaoService){
        this.licaoService = licaoService;
    }

    @GetMapping(value="/criar/{nome}")
    public String helloApp(@PathVariable String nome) {
        Licao licao = new Licao();
        licao.setNome(nome);
        licaoService.save(licao);
        return licao.getNome() + " criado!";
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Licao> getLicao(@PathVariable Long id){
        Optional<Licao> lesson = licaoService.findOne(id);
        if(lesson.isPresent()){
            return ResponseEntity.ok().body(lesson.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<Licao>> getLicoes() {
        List<Licao> licoes = licaoService.findAllList();
        if(licoes.size()>0){
            return ResponseEntity.ok().body(licoes);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping("/")
    // public ResponseEntity<Licao> createUsuario(@RequestBody Licao user) throws URISyntaxException{
    //     Long id = user.getId();
    //     if(id != null){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uma nova lição não pode ter um ID");
    //     }
    //     Licao licao = licaoService.save(user);
    //     return ResponseEntity.created(new URI("/api/licoes" + licao.getId())).body(licao);
    // } 
    @PostMapping("/")
    public ResponseEntity<Licao> createLicao(
            @RequestBody Licao licao
    ) throws URISyntaxException {
        log.debug("REST request to save Usuario: {}", licao);
        if (licao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo usuario não pode ter um ID");
        }
        Licao result = licaoService.save(licao);
        return ResponseEntity.created(new URI("/api/licoes/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicao(@PathVariable Long id){
        Optional<Licao> licao = licaoService.findOne(id);
        licaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Licao> updateLicao(@RequestBody Licao licao) throws URISyntaxException{
        if(licao.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lesson null id");
        }

        Licao lesson = licaoService.save(licao);
        return ResponseEntity.ok().body(lesson);
    }
    

}
