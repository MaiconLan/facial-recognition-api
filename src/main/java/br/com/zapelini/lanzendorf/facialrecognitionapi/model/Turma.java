package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.TurmaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
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
    private List<Aluno> alunos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor")
    private Professor professor;

    public Turma(TurmaDTO turma) {
        this.materia = turma.getMateria();
        this.ano = turma.getAno();
        this.periodo = turma.getPeriodo();
        this.tipo = turma.getTipo();
        this.finalizada = turma.getFinalizada();
    }
}
