package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.turma;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>, TurmaRepositoryQuery {

}



