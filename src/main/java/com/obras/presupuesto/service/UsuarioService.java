package com.obras.presupuesto.service;

import com.obras.presupuesto.dto.RegistroUsuarioRequest;
import com.obras.presupuesto.model.Usuario;
import com.obras.presupuesto.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.security.username:admin}")
    private String defaultUsername;

    @Value("${app.security.password:admin123}")
    private String defaultPassword;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void ensureDefaultUser() {
        if (!usuarioRepository.existsByUsername(defaultUsername)) {
            Usuario usuario = new Usuario();
            usuario.setUsername(defaultUsername);
            usuario.setPassword(passwordEncoder.encode(defaultPassword));
            usuario.setRol("ROLE_USER");
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void registrar(RegistroUsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.username().trim());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol("ROLE_USER");
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(usuario.getRol()))
        );
    }
}
