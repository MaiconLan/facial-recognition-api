package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.foto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
}
