package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDashboardDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno.AlunoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
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

    @GetMapping("/dashboard")
    public ResponseEntity<AlunoDashboardDTO> getDadosDashboard() {
        return ResponseEntity.ok(alunoService.getDadosDashboard());
    }

    @PostMapping("{id}/foto")
    public ResponseEntity<String> uploadFoto(@PathVariable(name = "id") Long idAluno,
                                             @RequestParam MultipartFile foto) throws IOException, ApiException {
        return ResponseEntity.ok(alunoService.uploadFoto(idAluno, foto));
    }

}
