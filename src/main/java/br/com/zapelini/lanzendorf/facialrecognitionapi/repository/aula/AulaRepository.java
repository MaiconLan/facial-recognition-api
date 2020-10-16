package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aula;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByTurma(Turma turma);

    @Query("SELECT count(a) FROM Aula a WHERE to_date(to_char(inicio, 'YYYY/MM/DD'), 'YYYY/MM/DD') = current_date")
    Long aulasdoDia();

}
