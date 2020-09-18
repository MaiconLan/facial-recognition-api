package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Coordenador;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordenadorDTO extends UsuarioDTO {

    public CoordenadorDTO(Coordenador coordenador) {
        super(coordenador.getUsuario());
    }

    public CoordenadorDTO(String nome, String email, String usuario, String senha) {
        super(nome, email, usuario, senha);
    }

}
