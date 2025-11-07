package com.ada.pokedex.service;

import com.ada.pokedex.model.Role;
import com.ada.pokedex.model.Usuario;
import com.ada.pokedex.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    private final UsuarioRepository usuarioRepository;

    public AdminService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void promoverParaAdmin(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário " + username + " não encontrado."));

        if (usuario.getRole() == Role.ROLE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário " + username + " já é um ADMIN.");
        }

        usuario.setRole(Role.ROLE_ADMIN);
        usuarioRepository.save(usuario);
    }
}