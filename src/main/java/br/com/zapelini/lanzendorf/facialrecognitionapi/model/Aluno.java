package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "aluno")
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Aluno extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long idAluno;

    private String matricula;

    @Override
    public String getTipo() {
        return "Aluno";
    }
}
