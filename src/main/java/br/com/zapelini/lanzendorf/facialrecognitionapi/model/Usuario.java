package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String nome;

    private String email;

    private String usuario;

    private String senha;

    @OneToOne(mappedBy = "usuario")
    private Professor professor;

    @OneToOne(mappedBy = "usuario")
    private Aluno aluno;

    @OneToOne(mappedBy = "usuario")
    private Coordenador coordenador;

    @OneToOne(mappedBy = "usuario")
    private Administrador administrador;

    public Usuario(UsuarioDTO usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.usuario = usuario.getUsuario();
        this.senha = usuario.getSenha();
    }

    public boolean isProfessor() {
        return professor != null;
    }

    public boolean isCoordenador() {
        return coordenador != null;
    }

    public boolean isAdministrador() {
        return administrador != null;
    }

    public boolean isAluno() {
        return aluno != null;
    }
}
