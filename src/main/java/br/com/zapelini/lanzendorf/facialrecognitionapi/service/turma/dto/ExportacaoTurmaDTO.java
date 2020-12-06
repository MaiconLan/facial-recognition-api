package br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Tipo;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto.AulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor.dto.ProfessorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExportacaoTurmaDTO {


    private Long idTurma;

    private String materia;

    private Integer ano;

    private String periodo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private Boolean finalizada;

    private ProfessorDTO professor;

    private List<AlunoDTO> alunos = new ArrayList<>();
    private List<AulaDTO> aulas = new ArrayList<>();

    public ExportacaoTurmaDTO(Turma turma) {
        this.idTurma = turma.getIdTurma();
        this.materia = turma.getMateria();
        this.ano = turma.getAno();
        this.periodo = turma.getPeriodo();
        this.tipo = turma.getTipo();
        this.finalizada = turma.getFinalizada();
        this.professor = new ProfessorDTO(turma.getProfessor());
    }

}
