package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
public class AlunoEndpoint {

    @PostMapping
    public void criarAluno(AlunoDTO alunoDTO) {

    }

}
