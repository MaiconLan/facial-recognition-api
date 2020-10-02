package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/aluno")
public class AlunoEndpoint {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@RequestBody AlunoDTO alunoDTO) throws NoSuchAlgorithmException, ApiException {
        return ResponseEntity.ok(alunoService.criarAluno(alunoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable(name = "id") Long idAluno, @RequestBody AlunoDTO alunoDTO) throws ApiException, NoSuchAlgorithmException {
        return ResponseEntity.ok(alunoService.atualizarAluno(idAluno, alunoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable(name = "id") Long idAluno) throws ApiException {
        return ResponseEntity.ok(alunoService.getAlunoDTO(idAluno));
    }

    @GetMapping()
    public ResponseEntity<Page<AlunoDTO>> filtrar(Pageable pageable,
                                                   @PathParam("nome") String nome,
                                                   @PathParam("email") String email,
                                                   @PathParam("matricula") String matricula) {
        Page<AlunoDTO> alunos = alunoService.filtrar(pageable, nome, email, matricula);
        return !alunos.isEmpty() ? ResponseEntity.ok(alunos) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void excluir(@PathVariable(name = "id") Long idAluno) throws ApiException {
        alunoService.excluir(idAluno);
    }

}
