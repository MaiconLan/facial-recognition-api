package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador.dto.CoordenadorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.coordenador.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/coordenador")
public class CoordenadorEndpoint {
    
    @Autowired
    private CoordenadorService coordenadorService;

    @PostMapping
    public ResponseEntity<CoordenadorDTO> criarCoordenador(@RequestBody CoordenadorDTO coordenadorDTO) throws NoSuchAlgorithmException, ApiException {
        return ResponseEntity.ok(coordenadorService.criarCoordenador(coordenadorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> atualizarCoordenador(@PathVariable(name = "id") Long idCoordenador, @RequestBody CoordenadorDTO coordenadorDTO) throws ApiException, NoSuchAlgorithmException {
        return ResponseEntity.ok(coordenadorService.atualizarCoordenador(idCoordenador, coordenadorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> getCoordenador(@PathVariable(name = "id") Long idCoordenador) throws ApiException {
        return ResponseEntity.ok(coordenadorService.getCoordenadorDTO(idCoordenador));
    }

    @GetMapping()
    public ResponseEntity<List<CoordenadorDTO>> getCoordenador() {
        List<CoordenadorDTO> coordenadors = coordenadorService.getCoordenadores();
        return !coordenadors.isEmpty() ? ResponseEntity.ok(coordenadors) : ResponseEntity.noContent().build();
    }
    
}
