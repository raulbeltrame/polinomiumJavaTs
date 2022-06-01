package com.LuanGabriel.polinomium.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.LuanGabriel.polinomium.domain.Amigo;
import com.LuanGabriel.polinomium.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    
    Optional<Usuario> findById(Long id);

    @Query("select distinct u from Usuario u")
    List<Usuario> listarUsuarios();

    // @Query(value="INSERT INTO 'associacao_usuario_amigo'('fk_usuario','fk_amigo') VALUES(:idUsuario, :idAmigo)", nativeQuery = true)
    // public void addAmigo(@Param("idUsuario") Long idUsuario, @Param("idAmigo") Long idAmigo);

    @Query(value="SELECT * FROM tb_usuario AS u WHERE u.username=:username AND u.senha=:senha", nativeQuery = true)
    public Usuario autenticar(@Param("username") String username, @Param("senha") String senha);

    @Query(value="SELECT * FROM tb_usuario WHERE nome LIKE :email% AND id<>:id",nativeQuery = true)
    public Usuario encontrarPorEmail(@Param("email") String email, @Param("id") Long id);

    @Query(value="SELECT id FROM tb_usuario WHERE id IN (SELECT fk_amigo FROM associacao_usuario_amigo WHERE fk_usuario=:id)", nativeQuery = true)
    public List<Long> encontrarAmigos(@Param("id") Long id);
}
