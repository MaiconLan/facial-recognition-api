package br.com.zapelini.lanzendorf.facialrecognitionapi.security;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.LoginException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsuario(username);

        Usuario usuario = optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorreto"));

        return new User(usuario.getUsuario(), usuario.getSenha(), getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));

        return authorities;
    }
}
