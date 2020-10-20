package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.foto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    @Query("SELECT f FROM Foto f JOIN f.aluno a WHERE a.idAluno = :idAluno")
    List<Foto> findByIdAluno(@Param("idAluno") Long idAluno);

}
