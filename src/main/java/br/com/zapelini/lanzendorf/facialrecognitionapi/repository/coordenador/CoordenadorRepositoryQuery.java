package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.coordenador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Coordenador;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoordenadorRepositoryQuery {

    List<Coordenador> filter(Pageable pageable, String nome, String email);

    Integer filterCount(String nome, String email);
}
