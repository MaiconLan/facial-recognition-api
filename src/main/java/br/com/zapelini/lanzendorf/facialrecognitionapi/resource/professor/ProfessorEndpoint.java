package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.ProfessorService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.dto.ProfessorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorEndpoint {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> criarProfessor(@RequestBody ProfessorDTO professorDTO) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(professorService.criarProfessor(professorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizarProfessor(@PathVariable(name = "id") Long idProfessor, @RequestBody ProfessorDTO professorDTO) throws ApiException, NoSuchAlgorithmException {
        return ResponseEntity.ok(professorService.atualizarProfessor(idProfessor, professorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable(name = "id") Long idProfessor) throws ApiException {
        return ResponseEntity.ok(new ProfessorDTO(professorService.getProfessor(idProfessor)));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getProfessor() {
        List<ProfessorDTO> professores = professorService.getProfessores();
        return !professores.isEmpty() ? ResponseEntity.ok(professores) : ResponseEntity.noContent().build();
    }

}
