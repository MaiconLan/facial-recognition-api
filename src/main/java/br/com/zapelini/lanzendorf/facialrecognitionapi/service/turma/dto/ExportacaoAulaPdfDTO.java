package br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExportacaoAulaPdfDTO {

    private String titulo;
    private String data;
    private String inicio;
    private String termino;

    private List<ExportacaoPresencaPdfDTO> presencas = new ArrayList<>();

    public ExportacaoAulaPdfDTO(Aula aula) {
        this.titulo = aula.getTitulo();
        this.data = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(aula.getInicio());
        this.inicio = DateTimeFormatter.ofPattern("HH:mm").format(aula.getInicio());
        this.termino = DateTimeFormatter.ofPattern("HH:mm").format(aula.getTermino());
        this.presencas = aula.getPresencas().stream().map(ExportacaoPresencaPdfDTO::new).collect(Collectors.toList());
    }
}
