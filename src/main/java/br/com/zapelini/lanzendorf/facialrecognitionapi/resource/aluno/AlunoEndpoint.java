package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoEndpoint {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.ok(alunoService.criarAluno(alunoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable(name = "id") Long idAluno, @RequestBody AlunoDTO alunoDTO) throws ApiException {
        return ResponseEntity.ok(alunoService.atualizarAluno(idAluno, alunoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable(name = "id") Long idAluno) throws ApiException {
        return ResponseEntity.ok(alunoService.getAlunoDTO(idAluno));
    }

}
