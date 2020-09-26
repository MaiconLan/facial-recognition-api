package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, AlunoRepositoryQuery {


}
