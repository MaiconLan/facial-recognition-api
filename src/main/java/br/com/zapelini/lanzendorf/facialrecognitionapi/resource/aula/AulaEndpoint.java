package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.RecursoInexistenteException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto.AulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aula.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aula")
public class AulaEndpoint {

    @Autowired
    private AulaService aulaService;

    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> getAula(@PathVariable("id") Long idAula) throws RecursoInexistenteException {
        return ResponseEntity.ok(aulaService.getAulaDTO(idAula));
    }

    @GetMapping("/turma/{id}")
    public ResponseEntity<List<AulaDTO>> getAulas(@PathVariable("id") Long idTurma) throws RecursoInexistenteException {
        return ResponseEntity.ok(aulaService.getAulasDTO(idTurma));
    }

    @PostMapping("/{id}/reconhecimento")
    public void detectarSala(@PathVariable("id") Long idAula,
                             @RequestParam(name = "foto") MultipartFile foto) throws IOException, ApiException {
        aulaService.detectarVariosRostos(idAula, foto);
    }

}
