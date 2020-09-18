package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.coordenador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
}
