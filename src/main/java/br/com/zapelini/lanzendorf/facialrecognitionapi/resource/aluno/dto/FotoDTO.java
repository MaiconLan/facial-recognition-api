package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FotoDTO {

    private Long idFoto;
    private String nome;
    private byte[] foto;

    private AlunoDTO aluno;

    public FotoDTO(Foto foto) {
        this.idFoto = foto.getIdFoto();
        this.nome = foto.getNome() + "." + foto.getExtensao();
    }
}
