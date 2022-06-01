package com.LuanGabriel.polinomium.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.LuanGabriel.polinomium.domain.Amigo;
import com.LuanGabriel.polinomium.domain.Usuario;
import com.LuanGabriel.polinomium.service.AmigoService;
import com.LuanGabriel.polinomium.service.UsuarioService;

import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path="/usuarios")

public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final AmigoService amigoService;
    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    public UsuarioResource(UsuarioService usuarioService, AmigoService amigoService){
        this.usuarioService = usuarioService;
        this.amigoService = amigoService;
    }

    @GetMapping(value="/criar/{nome}")
    public String helloApp(@PathVariable String nome){
        Usuario user = new Usuario();
        user.setNome(nome);
        usuarioService.save(user);
        return user.getNome() + " criado!";
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.findOne(id).get();
        if(usuario!=null){
            return ResponseEntity.ok().body(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> lista = usuarioService.findAllList();
        if(lista.size()>0){
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/{username}/{senha}/exists")
    public ResponseEntity<Usuario> usuarioExiste(@PathVariable String username, @PathVariable String senha){
        Usuario usuarioExiste = usuarioService.autenticar(username,senha);
        if(usuarioExiste!=null){
            return ResponseEntity.ok().body(usuarioExiste);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/email/{email}/id/{id}")
    public ResponseEntity<Usuario> encontrarPorEmail(@PathVariable String email, @PathVariable Long id){
        Usuario usuarioExiste = usuarioService.encontrarPorEmail(email, id);
        if(usuarioExiste!=null){
            return ResponseEntity.ok().body(usuarioExiste);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/{id}/amigos")
    public ResponseEntity<List<Usuario>> encontrarAmigos(@PathVariable Long id){
        List<Long> amigosId = usuarioService.encontrarAmigos(id);
        List<Usuario> amigos = new ArrayList<Usuario>();
        for(int i = 0; i < amigosId.size(); i++){
            amigos.add(
                usuarioService.findOne(amigosId.get(i)).get()
                );
        }
        if(amigos.size()>0){
            return ResponseEntity.ok().body(amigos);
        }else{
            return ResponseEntity.ok().body(new ArrayList<Usuario>());
        }
    }


    // @PostMapping("/")
    // public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario user) throws URISyntaxException{
    //     Long id = user.getId();
    //     if(id != null){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo usuário não pode ter um ID");
    //     }
    //     Usuario usuario = usuarioService.save(user);
    //     return ResponseEntity.created(new URI("/api/usuarios" + usuario.getId())).body(usuario);
    // }

    @PostMapping("/")
    public ResponseEntity<Usuario> createUsuario(
            @RequestBody Usuario usuario
    ) throws URISyntaxException {
        log.debug("REST request to save Usuario: {}", usuario);
        if (usuario.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo usuario não pode ter um ID");
        }
        Usuario result = usuarioService.save(usuario);
        Amigo amigo = new Amigo();
        amigo.setId(usuario.getId());
        amigoService.save(amigo);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
                .body(result);
    }

    @PostMapping("/{idUsuario}/addAmigo/{idAmigo}")
    public ResponseEntity<Usuario> addAmigo(@PathVariable Long idAmigo, @PathVariable Long idUsuario){
        Usuario usuario = usuarioService.findOne(idUsuario).get();
        Amigo amigo = amigoService.findOne(idAmigo).get();
        usuario.getAmigos().add(amigo);
        amigo.getUsuarios().add(usuario);
        usuarioService.save(usuario);
        amigoService.save(amigo);
        // usuarioService.addAmigo(idUsuario, idAmigo);
        return ResponseEntity.ok().body(usuario);
    }


    // @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public List<Usuario> upload(@RequestPart("data") MultipartFile csv) throws IOException {
    //     List<Usuario> savedNotes  = new ArrayList<>();
    //     List<Usuario> notes = new BufferedReader(
    //             new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
    //             .map(Usuario::parseNote).collect(Collectors.toList());
    //     usuarioService.saveAll(notes).forEach(savedNotes::add);
    //     return savedNotes;
    // }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findOne(id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idUsuario}/{idAmigo}")
    public ResponseEntity<Void> deleteAmigo(@PathVariable Long idUsuario, @PathVariable Long idAmigo){
        Usuario usuario = usuarioService.findOne(idUsuario).get();
        Amigo amigo = amigoService.findOne(idAmigo).get();
        usuario.getAmigos().remove(amigo);
        amigo.getUsuarios().remove(usuario);
        usuarioService.save(usuario);
        amigoService.save(amigo);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario user) throws URISyntaxException{
        if(user.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user null id");
        }

        Usuario usuario = usuarioService.save(user);
        return ResponseEntity.ok().body(usuario);
    }
    
}