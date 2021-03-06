package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;

    private String nome;

    private String email;

    private String usuario;

    private String senha;

    public UsuarioDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.usuario = usuario.getUsuario();
    }

    public UsuarioDTO(String nome, String email, String usuario, String senha) {
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

}
