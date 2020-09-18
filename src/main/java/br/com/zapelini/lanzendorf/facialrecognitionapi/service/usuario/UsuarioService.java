package br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.usuario.UsuarioRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.usuario.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveUsuario(UsuarioDTO usuarioDTO, Usuario usuario) throws NoSuchAlgorithmException {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsuario(usuarioDTO.getUsuario());

        if(StringUtils.isEmpty(usuarioDTO.getSenha()))
            return usuarioRepository.save(usuario);

        usuario.setSenha(criptofragar(usuarioDTO.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public String criptofragar(String cript) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = algorithm.digest(cript.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }

}
