package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.ProfessorService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.dto.ProfessorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professor")
public class ProfessorEndpoint {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> criarProfessor(@RequestBody ProfessorDTO professorDTO) {
        return ResponseEntity.ok(professorService.criarProfessor(professorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizarAluno(@PathVariable(name = "id") Long idProfessor, @RequestBody ProfessorDTO professorDTO) throws ApiException {
        return ResponseEntity.ok(professorService.atualizarProfessor(idProfessor, professorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getAluno(@PathVariable(name = "id") Long idProfessor) throws ApiException {
        return ResponseEntity.ok(new ProfessorDTO(professorService.getProfessor(idProfessor)));
    }

}
