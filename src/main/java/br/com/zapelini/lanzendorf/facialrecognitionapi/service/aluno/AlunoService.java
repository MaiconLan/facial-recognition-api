package br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno.AlunoRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) throws NoSuchAlgorithmException {
        Aluno aluno = new Aluno(alunoDTO);

        Usuario usuario = usuarioService.saveUsuario(alunoDTO, aluno.getUsuario());

        aluno.setUsuario(usuario);

        return new AlunoDTO(alunoRepository.save(aluno));
    }

    public AlunoDTO atualizarAluno(Long idAluno, AlunoDTO alunoDTO) throws ApiException, NoSuchAlgorithmException {
        Aluno alunoSaved = getAluno(idAluno);
        Aluno aluno = new Aluno(alunoDTO);

        BeanUtils.copyProperties(aluno, alunoSaved, "idAluno", "usuario");
        Usuario usuario = usuarioService.saveUsuario(alunoDTO, alunoSaved.getUsuario());

        alunoSaved.setUsuario(usuario);
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

    public List<AlunoDTO> getAlunos() {
        return alunoRepository.findAll().stream().map(AlunoDTO::new).collect(Collectors.toList());
    }
}