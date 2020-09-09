package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoDTO {

    public AlunoDTO(Aluno aluno) {
        aluno.getTipo()
    }
}
