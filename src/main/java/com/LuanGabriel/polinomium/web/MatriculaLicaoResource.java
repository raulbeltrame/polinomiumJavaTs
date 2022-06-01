package com.LuanGabriel.polinomium.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Optional.*;

import com.LuanGabriel.polinomium.domain.Licao;
import com.LuanGabriel.polinomium.domain.MatriculaLicao;
import com.LuanGabriel.polinomium.domain.Usuario;
import com.LuanGabriel.polinomium.service.LicaoService;
import com.LuanGabriel.polinomium.service.MatriculaLicaoService;
import com.LuanGabriel.polinomium.service.UsuarioService;

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
@RequestMapping("/matriculaLicoes")
public class MatriculaLicaoResource {
    
    private final MatriculaLicaoService matriculaLicaoService;
    private final UsuarioService usuarioService;
    private final LicaoService licaoService;
    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    public MatriculaLicaoResource(MatriculaLicaoService matriculaLicaoService, UsuarioService usuarioService, LicaoService licaoService){
        this.matriculaLicaoService = matriculaLicaoService;
        this.usuarioService = usuarioService;
        this.licaoService = licaoService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<MatriculaLicao> getMatriculaLicao(@PathVariable Long id){
        Optional<MatriculaLicao> matricula = matriculaLicaoService.findOne(id);
        if(matricula.isPresent()){
            return ResponseEntity.ok().body(matricula.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/{idLicao}/{idUsuario}")
    public ResponseEntity<MatriculaLicao> getMatriculaLicaoPorUsuario(@PathVariable Long idLicao, @PathVariable Long idUsuario){
        MatriculaLicao matricula = matriculaLicaoService.getByUsuario(idLicao,idUsuario);
        if(matricula!=null){
            return ResponseEntity.ok().body(matricula);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<MatriculaLicao>> getMatriculasLicao() {
        List<MatriculaLicao> matriculasLicao = matriculaLicaoService.findAllList();
        if(matriculasLicao.size()>0){
            return ResponseEntity.ok().body(matriculasLicao);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<MatriculaLicao> createMatriculaLicao(
            @RequestBody MatriculaLicao matriculaLicao
    ) throws URISyntaxException {
        log.debug("REST request to save MatriculaLicao: {}", matriculaLicao);
        if (matriculaLicao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova matrícula não pode ter um ID");
        }
       //tenho que adicionar a matricula em lição e usuario
        Licao licao = licaoService.findOne(
            matriculaLicao.getLicao().getId()
        ).get();

        licao.getMatriculaLicao().add(matriculaLicao);
        licaoService.save(licao);

        Usuario user = usuarioService.findOne(
            matriculaLicao.getUsuario().getId()
        ).get();

        user.getMatriculaLicao().add(matriculaLicao);
        usuarioService.save(user);

        MatriculaLicao result = matriculaLicaoService.save(matriculaLicao);
        return ResponseEntity.created(new URI("/api/matriculasLicao/" + result.getId()))
                .body(result);
    }

    @PostMapping("/{idUsuario}/{idLicao}")
    public ResponseEntity<MatriculaLicao> addMatriculaLicao(@PathVariable Long idUsuario, @PathVariable Long idLicao) throws URISyntaxException{
       //tenho que adicionar a matricula em lição e usuario
        MatriculaLicao matriculaLicao = new MatriculaLicao();
        matriculaLicao.setIsFinished(true);
        matriculaLicao.setIsLocked(false);
        
        Licao licao = licaoService.findOne(
            idLicao
            ).get();
            
        licao.getMatriculaLicao().add(matriculaLicao);
        licaoService.save(licao);
        
        Usuario user = usuarioService.findOne(
            idUsuario
        ).get();
                
        matriculaLicao.setUsuario(user);
        matriculaLicao.setLicao(licao);
        user.getMatriculaLicao().add(matriculaLicao);
        usuarioService.save(user);
        matriculaLicao.setIsFinished(true);
        MatriculaLicao result = matriculaLicaoService.save(matriculaLicao);
        Boolean atualizou = atualizarLicoes(idUsuario, idLicao);
        return ResponseEntity.created(new URI("/api/matriculasLicao/" + result.getId()))
                .body(result);
    }

    @PostMapping("/introduzir/{idUsuario}")
    public ResponseEntity<MatriculaLicao> introduzir(@PathVariable Long idUsuario) throws URISyntaxException{
       //tenho que adicionar a matricula em lição e usuario
        MatriculaLicao matriculaLicao = new MatriculaLicao();
        matriculaLicao.setIsFinished(false);
        matriculaLicao.setIsLocked(true);
        Licao licao = licaoService.findOne(Long.valueOf(1)).get();
            
        licao.getMatriculaLicao().add(matriculaLicao);
        licaoService.save(licao);
        
        Usuario user = usuarioService.findOne(
            idUsuario
        ).get();
                
        matriculaLicao.setUsuario(user);
        matriculaLicao.setLicao(licao);
        user.getMatriculaLicao().add(matriculaLicao);
        usuarioService.save(user);
        MatriculaLicao result = matriculaLicaoService.save(matriculaLicao);
        return ResponseEntity.created(new URI("/api/matriculasLicao/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatriculaLicao(@PathVariable Long id){
        Optional<MatriculaLicao> matriculaLicao = matriculaLicaoService.findOne(id);
        matriculaLicaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<MatriculaLicao> updateMatriculaLicao(@RequestBody MatriculaLicao matriculaLicao) throws URISyntaxException{
        if(matriculaLicao.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lesson null id");
        }

        MatriculaLicao matriculaLesson = matriculaLicaoService.save(matriculaLicao);
        return ResponseEntity.ok().body(matriculaLesson);
    }
    
    public Boolean atualizarLicoes(Long idUsuario, Long idLicao){
        List<Licao> licoes = licaoService.findAllList();
        MatriculaLicao matLicao = new MatriculaLicao();
        matLicao.setIsFinished(false);
        matLicao.setIsLocked(false);
        Licao licao = licaoService.findOne(idLicao+1).get();
        matLicao.setLicao(licao);
        Usuario usuario = usuarioService.findOne(idUsuario).get();
        matLicao.setUsuario(usuario);
        MatriculaLicao matriculaLesson = matriculaLicaoService.save(matLicao);
        return true;
    }

}
