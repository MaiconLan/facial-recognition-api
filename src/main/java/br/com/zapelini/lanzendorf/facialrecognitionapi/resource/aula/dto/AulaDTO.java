package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.CadastroAulaDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AulaDTO extends CadastroAulaDTO {

    private List<PresencaDTO> presencas;

    public AulaDTO(Aula aula) {
        super(aula);
        presencas = aula.getPresencas().stream().map(PresencaDTO::new).collect(Collectors.toList());
    }
}
