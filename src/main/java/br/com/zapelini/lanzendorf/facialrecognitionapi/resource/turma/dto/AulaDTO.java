package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AulaDTO {

    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;

    public AulaDTO(Aula aula) {
        this.id = aula.getIdAula();
        this.title = aula.getTitulo();
        this.start = aula.getInicio();
        this.end = aula.getTermino();
    }
}
