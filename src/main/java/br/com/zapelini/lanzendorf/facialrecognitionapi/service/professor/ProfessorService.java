package br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.professor.ProfessorRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.dto.ProfessorDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorDTO criarProfessor(ProfessorDTO professorDTO) {
        Professor professor = new Professor(professorDTO);
        return new ProfessorDTO(professorRepository.save(professor));
    }

    public ProfessorDTO atualizarProfessor(Long idProfessor, ProfessorDTO professorDTO) throws ApiException {
        Professor professorSaved = getProfessor(idProfessor);
        Usuario usuarioSaved = professorSaved.getUsuario();

        Professor professor = new Professor(professorDTO);
        Usuario usuario = professor.getUsuario();

        BeanUtils.copyProperties(professor, professorSaved, "idProfessor", "usuario");
        BeanUtils.copyProperties(usuario, usuarioSaved, "idUsuario", "senha");

        professorSaved.setUsuario(usuarioSaved);
        professorRepository.save(professorSaved);

        return getProfessorDTO(idProfessor);
    }

    public Professor getProfessor(Long idProfessor) throws ApiException {
        return professorRepository.findById(idProfessor).orElseThrow(() -> new ApiException("Aluno não encontrado!"));
    }

    public ProfessorDTO getProfessorDTO(Long idProfessor) throws ApiException {
        Professor professor = getProfessor(idProfessor);
        return new ProfessorDTO(professor);
    }
}
