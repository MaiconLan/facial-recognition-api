package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor.dto.ProfessorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/professor")
public class ProfessorEndpoint {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> criarProfessor(@RequestBody ProfessorDTO professorDTO) throws NoSuchAlgorithmException, ApiException {
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
    public ResponseEntity<Page<ProfessorDTO>> filtar(Pageable pageable,
                                                     @PathParam("nome") String nome,
                                                     @PathParam("email") String email) {
        Page<ProfessorDTO> professores = professorService.filtrar(pageable, nome, email);
        return !professores.isEmpty() ? ResponseEntity.ok(professores) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void excluir(@PathVariable(name = "id") Long idProfessor) throws ApiException {
        professorService.excluir(idProfessor);
    }

}
