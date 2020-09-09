package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Long idTurma;

    private String materia;

    private Integer ano;

    private String periodo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private Boolean finalizada;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "turma_aluno",
            joinColumns = @JoinColumn(name = "id_turma"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno"))
    private List<Aluno> alunos;

}
