package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlunoRepositoryQuery {

    List<Aluno> filter(Pageable pageable, String nome, String email, String matricula);

    Integer filterCount(String nome, String email, String matricula);

}
