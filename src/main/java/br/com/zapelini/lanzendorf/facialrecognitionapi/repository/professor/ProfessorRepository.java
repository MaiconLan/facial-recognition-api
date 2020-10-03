package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryQuery {

}
