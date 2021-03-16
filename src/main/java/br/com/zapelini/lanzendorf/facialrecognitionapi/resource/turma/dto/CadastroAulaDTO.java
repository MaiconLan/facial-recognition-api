package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroAulaDTO {

    private Long id;
    private String title;
    private LocalDate date;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime start;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime end;

    public CadastroAulaDTO(Aula aula) {
        this.id = aula.getIdAula();
        this.title = aula.getTitulo();
        this.date = aula.getInicio().toLocalDate();
        this.start = aula.getInicio().toLocalTime();
        this.end = aula.getTermino().toLocalTime();
    }
}
