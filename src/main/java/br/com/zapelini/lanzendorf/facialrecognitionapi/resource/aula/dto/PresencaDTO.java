package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Presenca;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresencaDTO {

    private Long idPresenca;
    private Boolean presenca;

    private AlunoDTO aluno;

    public PresencaDTO(Presenca presenca) {
        this.idPresenca = presenca.getIdPresenca();
        this.presenca = presenca.getPresenca();
        this.aluno = new AlunoDTO(presenca.getAluno());
    }
}
