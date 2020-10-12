package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.AulaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Aula(AulaDTO aulaDTO) {
        this.titulo = aulaDTO.getTitle();
        this.inicio = aulaDTO.getStart();
        this.termino = aulaDTO.getEnd();
    }
}
