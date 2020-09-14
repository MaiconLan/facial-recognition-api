package br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfessorDTO extends UsuarioDTO {

    public ProfessorDTO(Professor professor) {
        super(professor.getUsuario().getNome(), professor.getUsuario().getEmail(), professor.getUsuario().getUsuario(), null);
    }

    public ProfessorDTO(String nome, String email, String usuario, String senha) {
        super(nome, email, usuario, senha);
    }
}
