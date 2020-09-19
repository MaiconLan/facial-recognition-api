package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.usuario;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByUsuarioAndIdUsuarioNot(String usuario, Long idUsuario);

    Boolean existsByEmailAndIdUsuarioNot(String email, Long idUsuario);

    Boolean existsByUsuario(String usuario);

    Boolean existsByEmail(String email);
}
