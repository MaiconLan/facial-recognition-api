package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "presenca")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presenca")
    private Long idPresenca;

    private Boolean presenca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    public Presenca(Aula aula, Aluno aluno) {
        this.aula = aula;
        this.aluno = aluno;
    }
}
