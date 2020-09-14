package br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno.AlunoRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno(alunoDTO);
        return new AlunoDTO(alunoRepository.save(aluno));
    }

    public AlunoDTO atualizarAluno(Long idAluno, AlunoDTO alunoDTO) throws ApiException {
        Aluno alunoSaved = getAluno(idAluno);
        Usuario usuarioSaved = alunoSaved.getUsuario();

        Aluno aluno = new Aluno(alunoDTO);
        Usuario usuario = aluno.getUsuario();

        BeanUtils.copyProperties(aluno, alunoSaved, "idAluno", "usuario");
        BeanUtils.copyProperties(usuario, usuarioSaved, "idUsuario", "senha");

        alunoSaved.setUsuario(usuarioSaved);
        alunoRepository.save(alunoSaved);

        return getAlunoDTO(idAluno);
    }

    public Aluno getAluno(Long idAluno) throws ApiException {
        return alunoRepository.findById(idAluno).orElseThrow(() -> new ApiException("Aluno não encontrado!"));
    }

    public AlunoDTO getAlunoDTO(Long idAluno) throws ApiException {
        Aluno aluno = getAluno(idAluno);
        return new AlunoDTO(aluno);
    }
}
