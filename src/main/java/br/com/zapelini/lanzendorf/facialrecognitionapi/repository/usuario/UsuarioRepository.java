package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.usuario;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
