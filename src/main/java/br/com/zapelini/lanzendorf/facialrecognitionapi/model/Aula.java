package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.CadastroAulaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long idAula;

    private String titulo;

    private LocalDateTime inicio;

    private LocalDateTime termino;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @OneToMany(mappedBy = "aula")
    private List<Presenca> presencas;

    public Aula(CadastroAulaDTO aulaDTO) {
        this.titulo = aulaDTO.getTitle();
        this.inicio = LocalDateTime.of(aulaDTO.getDate(), aulaDTO.getStart());
        this.termino = LocalDateTime.of(aulaDTO.getDate(), aulaDTO.getEnd());
    }
}
