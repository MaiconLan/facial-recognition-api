package br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExportacaoTurmaPdfDTO {

    private String turma;
    private String professor;

    private List<ExportacaoAulaPdfDTO> aulas = new ArrayList<>();

    public ExportacaoTurmaPdfDTO(Turma turma) {
        this.professor = turma.getProfessor().getUsuario().getNome();
        this.turma = turma.getMateria() + " - " + turma.getAno() + "/" + turma.getPeriodo();
    }
}
