package mx.unam.banunam.auth.usuario.repository;

import mx.unam.banunam.auth.usuario.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario,Integer> {
    Optional<TipoUsuario> findByAlias(String alias);
    List<TipoUsuario> findAllByOrderByTipoUsuarioAsc();
    List<TipoUsuario> findAllByOrderByAliasAsc();
}