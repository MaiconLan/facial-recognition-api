package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.RecursoInexistenteException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.AulaDashboardDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.CadastroAulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.TurmaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaEndpoint {

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/filtro")
    public ResponseEntity<Page<TurmaDTO>> filtrar(Pageable pageable,
                                                  @RequestHeader Long idUsuario,
                                                  @PathParam("materia") String materia,
                                                  @PathParam("periodo") String periodo,
                                                  @PathParam("tipo") String tipo,
                                                  @PathParam("finalizada") Boolean finalizada) throws RecursoInexistenteException {
        Page<TurmaDTO> turmas = turmaService.filtrar(pageable, idUsuario, materia, periodo, tipo, finalizada);
        return !turmas.isEmpty() ? ResponseEntity.ok(turmas) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listar(@RequestHeader Long idUsuario) throws RecursoInexistenteException {
        List<TurmaDTO> turmas = turmaService.listar(idUsuario);
        return !turmas.isEmpty() ? ResponseEntity.ok(turmas) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody TurmaDTO turmaDTO) throws ApiException {
        return ResponseEntity.ok(turmaService.criarTurma(turmaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable(name = "id") Long idTurma, @RequestBody TurmaDTO turmaDTO) throws ApiException {
        return ResponseEntity.ok(turmaService.atualizarTurma(idTurma, turmaDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<TurmaDTO> buscarTurma(@PathVariable(name = "id") Long idTurma) throws ApiException {
        return ResponseEntity.ok(turmaService.getTurmaDTO(idTurma));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void remover(@PathVariable(name = "id") Long idTurma) throws ApiException {
        turmaService.excluir(idTurma);
    }

    @PostMapping("/{id}/aula")
    public ResponseEntity<CadastroAulaDTO> criarAula(@PathVariable(name = "id") Long idTurma, @RequestBody CadastroAulaDTO aulaDTO) throws ApiException {
        return ResponseEntity.ok(turmaService.criarAula(idTurma, aulaDTO));
    }

    @PutMapping("/aula/{id}")
    public ResponseEntity<CadastroAulaDTO> atualizarTurma(@PathVariable(name = "id") Long idAula, @RequestBody CadastroAulaDTO aulaDTO) throws ApiException {
        return ResponseEntity.ok(turmaService.atualizarTurma(idAula, aulaDTO));
    }

    @GetMapping("/{id}/aula")
    public ResponseEntity<List<CadastroAulaDTO>> getAulas(@PathVariable(name = "id") Long idTurma) throws RecursoInexistenteException {
        return ResponseEntity.ok(turmaService.getAulas(idTurma));
    }

    @GetMapping("/aula/{id}")
    public ResponseEntity<CadastroAulaDTO> getAula(@PathVariable(name = "id") Long idAula) throws RecursoInexistenteException {
        return ResponseEntity.ok(turmaService.getAulaDTO(idAula));
    }

    @DeleteMapping("/aula/{id}")
    public void removerAula(@PathVariable(name = "id") Long idAula) throws RecursoInexistenteException {
        turmaService.removerAula(idAula);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AulaDashboardDTO> getDadosDashboard() {
        return ResponseEntity.ok(turmaService.getDadosDashboard());
    }

    @PutMapping(value = "/{id}/exportacao", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportarAulas(@PathVariable(name = "id") Long idTurma,
                                                @PathParam("formato") String formato) throws Exception {
        return ResponseEntity.ok(turmaService.exportarAulas(idTurma, formato));
    }

}
