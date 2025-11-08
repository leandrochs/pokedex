package com.ada.pokedex.service;

import com.ada.pokedex.dto.auth.AuthResponseDTO;
import com.ada.pokedex.dto.auth.LoginRequestDTO;
import com.ada.pokedex.dto.auth.RegisterRequestDTO;
import com.ada.pokedex.model.Role;
import com.ada.pokedex.model.Usuario;
import com.ada.pokedex.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (usuarioRepository.findByUsername(dto.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já está em uso.");
        }

        Usuario usuario = new Usuario(
                dto.username(),
                passwordEncoder.encode(dto.password()),
                Role.ROLE_USER // <-- Todo novo usuário é ROLE_USER
        );
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario);
        return new AuthResponseDTO(token);
    }
    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );
        Usuario usuario = usuarioRepository.findByUsername(dto.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuário não encontrado após autenticação."));

        String token = jwtService.generateToken(usuario);
        return new AuthResponseDTO(token);
    }
}