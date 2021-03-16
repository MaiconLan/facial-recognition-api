package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.turma;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Tipo;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TurmaRepositoryQuery {

    List<Turma> filter(Pageable pageable, Professor professor, String materia, String periodo, Tipo tipo, Boolean finalizada);

    Long filterCount(String materia, Professor professor, String periodo, Tipo tipo, Boolean finalizada);

}
