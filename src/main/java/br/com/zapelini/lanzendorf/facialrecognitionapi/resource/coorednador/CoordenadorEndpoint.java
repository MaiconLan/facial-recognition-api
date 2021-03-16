package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador.dto.CoordenadorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.coordenador.CoordenadorService;
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

    @GetMapping
    public ResponseEntity<Page<CoordenadorDTO>> filtar(Pageable pageable,
                                                     @PathParam("nome") String nome,
                                                     @PathParam("email") String email) {
        Page<CoordenadorDTO> coordenadores = coordenadorService.filtrar(pageable, nome, email);
        return !coordenadores.isEmpty() ? ResponseEntity.ok(coordenadores) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void excluir(@PathVariable(name = "id") Long idCoordenador) throws ApiException {
        coordenadorService.excluir(idCoordenador);
    }
    
}
