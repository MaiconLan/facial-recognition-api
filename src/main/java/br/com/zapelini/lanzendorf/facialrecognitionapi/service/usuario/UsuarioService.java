package br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.usuario.UsuarioRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SenhaUtil senhaUtil;

    public Usuario saveUsuario(UsuarioDTO usuarioDTO, Usuario usuario) throws NoSuchAlgorithmException, ApiException {
        if (usuario.getIdUsuario() != null) {
            if (usuarioRepository.existsByUsuarioAndIdUsuarioNot(usuarioDTO.getUsuario(), usuario.getIdUsuario()))
                throw new ApiException("Este nome de usuário já está em uso!");

            if (usuarioRepository.existsByEmailAndIdUsuarioNot(usuarioDTO.getEmail(), usuario.getIdUsuario()))
                throw new ApiException("Este e-mail já está em uso!");
        } else {
            if (usuarioRepository.existsByUsuario(usuarioDTO.getUsuario()))
                throw new ApiException("Este nome de usuário já está em uso!");

            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail()))
                throw new ApiException("Este e-mail já está em uso!");
        }

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsuario(usuarioDTO.getUsuario());

        if (StringUtils.isEmpty(usuarioDTO.getSenha()))
            return usuarioRepository.save(usuario);

        usuario.setSenha(senhaUtil.criptografar(usuarioDTO.getSenha()));
        return usuarioRepository.save(usuario);
    }

}
