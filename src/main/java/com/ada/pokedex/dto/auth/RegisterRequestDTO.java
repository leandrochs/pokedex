package com.ada.pokedex.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record RegisterRequestDTO(
        @NotBlank String username,
        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String password
) {}