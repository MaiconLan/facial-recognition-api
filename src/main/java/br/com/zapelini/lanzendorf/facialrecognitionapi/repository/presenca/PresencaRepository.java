package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.presenca;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {


    Optional<Presenca> findPresencaByAlunoAndAula(Aluno aluno, Aula aula);
}
