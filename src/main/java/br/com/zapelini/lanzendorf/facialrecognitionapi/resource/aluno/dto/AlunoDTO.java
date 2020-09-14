package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoDTO extends UsuarioDTO {

    private String matricula;

    public AlunoDTO(Aluno aluno) {
        super(aluno.getUsuario().getNome(), aluno.getUsuario().getEmail(), aluno.getUsuario().getUsuario(), null);
        this.matricula = aluno.getMatricula();
    }
}
