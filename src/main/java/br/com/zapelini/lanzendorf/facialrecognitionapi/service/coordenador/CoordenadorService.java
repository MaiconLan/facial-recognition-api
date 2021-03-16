package br.com.zapelini.lanzendorf.facialrecognitionapi.service.coordenador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Coordenador;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.coordenador.CoordenadorRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.coorednador.dto.CoordenadorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private UsuarioService usuarioService;

    public CoordenadorDTO criarCoordenador(CoordenadorDTO coordenadorDTO) throws NoSuchAlgorithmException, ApiException {
        Coordenador coordenador = new Coordenador(coordenadorDTO);

        Usuario usuario = usuarioService.saveUsuario(coordenadorDTO, coordenador.getUsuario());

        coordenador.setUsuario(usuario);

        return new CoordenadorDTO(coordenadorRepository.save(coordenador));
    }

    public CoordenadorDTO atualizarCoordenador(Long idCoordenador, CoordenadorDTO coordenadorDTO) throws NoSuchAlgorithmException, ApiException {
        Coordenador coordenadorSaved = getCoordenador(idCoordenador);
        Coordenador coordenador = new Coordenador(coordenadorDTO);

        BeanUtils.copyProperties(coordenador, coordenadorSaved, "idCoordenador", "usuario");
        Usuario usuario = usuarioService.saveUsuario(coordenadorDTO, coordenadorSaved.getUsuario());

        coordenadorSaved.setUsuario(usuario);
        coordenadorRepository.save(coordenadorSaved);

        return getCoordenadorDTO(idCoordenador);
    }

    public Coordenador getCoordenador(Long idCoordenador) throws ApiException {
        return coordenadorRepository.findById(idCoordenador).orElseThrow(() -> new ApiException("Coordenador não encontrado!"));
    }

    public CoordenadorDTO getCoordenadorDTO(Long idCoordenador) throws ApiException {
        Coordenador coordenador = getCoordenador(idCoordenador);
        return new CoordenadorDTO(coordenador);
    }

    public Page<CoordenadorDTO> filtrar(Pageable pageable, String nome, String email) {
        List<CoordenadorDTO> coordenadores = coordenadorRepository.filter(pageable, nome, email)
                .stream()
                .map(CoordenadorDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(coordenadores,
                pageable,
                coordenadorRepository.filterCount(nome, email)
        );
    }

    public void excluir(Long idCoordenador) throws ApiException {
        Coordenador coordenador = getCoordenador(idCoordenador);
        coordenadorRepository.delete(coordenador);
    }

}
