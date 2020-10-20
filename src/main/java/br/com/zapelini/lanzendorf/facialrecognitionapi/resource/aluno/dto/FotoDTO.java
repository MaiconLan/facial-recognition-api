package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoDTO {

    private Long idFoto;
    private String nome;
    private byte[] foto;

}
