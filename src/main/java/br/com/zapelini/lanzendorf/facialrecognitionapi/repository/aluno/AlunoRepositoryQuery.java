package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;

import java.util.List;

public interface AlunoRepositoryQuery {

    List<Aluno> filter(String nome, String email, String matricula);

}
