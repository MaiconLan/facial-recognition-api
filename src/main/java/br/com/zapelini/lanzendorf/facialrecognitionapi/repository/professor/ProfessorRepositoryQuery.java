package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepositoryQuery {

    List<Professor> filter(Pageable pageable, String nome, String email);

    Integer filterCount(String nome, String email);

}
