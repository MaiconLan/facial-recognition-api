package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoDTO extends UsuarioDTO {

    private Long idAluno;
    private String matricula;

    public AlunoDTO(String nome, String email, String usuario, String senha, String matricula) {
        super(nome, email, usuario, senha);
        this.matricula = matricula;
    }

    public AlunoDTO(Aluno aluno) {
        super(aluno.getUsuario());
        this.idAluno = aluno.getIdAluno();
        this.matricula = aluno.getMatricula();
    }
}
