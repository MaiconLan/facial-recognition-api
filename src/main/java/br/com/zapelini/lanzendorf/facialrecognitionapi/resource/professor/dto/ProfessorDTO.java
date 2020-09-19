package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfessorDTO extends UsuarioDTO {

    private Long idProfessor;

    public ProfessorDTO(Professor professor) {
        super(professor.getUsuario());
        this.idProfessor = professor.getIdProfessor();
    }

    public ProfessorDTO(String nome, String email, String usuario, String senha) {
        super(nome, email, usuario, senha);
    }
}
