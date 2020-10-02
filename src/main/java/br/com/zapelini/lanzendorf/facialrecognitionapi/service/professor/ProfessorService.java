package br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.professor.ProfessorRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor.dto.ProfessorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioService usuarioService;

    public ProfessorDTO criarProfessor(ProfessorDTO professorDTO) throws NoSuchAlgorithmException, ApiException {
        Professor professor = new Professor(professorDTO);

        Usuario usuario = usuarioService.saveUsuario(professorDTO, professor.getUsuario());

        professor.setUsuario(usuario);

        return new ProfessorDTO(professorRepository.save(professor));
    }

    public ProfessorDTO atualizarProfessor(Long idProfessor, ProfessorDTO professorDTO) throws ApiException, NoSuchAlgorithmException {
        Professor professorSaved = getProfessor(idProfessor);
        Professor professor = new Professor(professorDTO);

        BeanUtils.copyProperties(professor, professorSaved, "idProfessor", "usuario");
        Usuario usuario = usuarioService.saveUsuario(professorDTO, professorSaved.getUsuario());

        professorSaved.setUsuario(usuario);
        professorRepository.save(professorSaved);

        return getProfessorDTO(idProfessor);
    }

    public Professor getProfessor(Long idProfessor) throws ApiException {
        return professorRepository.findById(idProfessor).orElseThrow(() -> new ApiException("Professor não encontrado!"));
    }

    public ProfessorDTO getProfessorDTO(Long idProfessor) throws ApiException {
        Professor professor = getProfessor(idProfessor);
        return new ProfessorDTO(professor);
    }

    public Page<ProfessorDTO> filtrar(Pageable pageable, String nome, String email) {
        List<ProfessorDTO> professores = professorRepository.filter(pageable, nome, email)
                .stream()
                .map(ProfessorDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(professores,
                pageable,
                professorRepository.filterCount(nome, email)
        );
    }
}
