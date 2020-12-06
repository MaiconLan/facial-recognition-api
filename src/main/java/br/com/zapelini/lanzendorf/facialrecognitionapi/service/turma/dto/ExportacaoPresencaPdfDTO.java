package br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Presenca;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

@Getter
@Setter
public class ExportacaoPresencaPdfDTO {

    private String aluno;
    private String presenca;

    public ExportacaoPresencaPdfDTO(Presenca presenca) {
        this.aluno = presenca.getAluno().getUsuario().getNome();
        this.presenca = BooleanUtils.isTrue(presenca.getPresenca()) ? "Sim" : "Não";
    }
}
