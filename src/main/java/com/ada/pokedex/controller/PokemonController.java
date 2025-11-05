package com.ada.pokedex.controller;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/pokemon")
@RestController
public class PokemonController {

    private final PokemonService pokemonService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarPokemon(@PathVariable Long id,@Valid @RequestBody Pokemon dadosAtualizados) {
        Optional<Pokemon> pokemonAtualizado = pokemonService.atualizarPokemon(id, dadosAtualizados);

        if (pokemonAtualizado.isPresent()) {
            log.info("Código HTTP: 200 OK - Pokémon atualizado com sucesso!");
            return ResponseEntity.ok(pokemonAtualizado.get());
        } else {
            log.warn("Código HTTP: 404 (Not Found) — o recurso solicitado (ID {}) é inexistente.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Pokemon> opt = pokemonService.findById(id);
        if (opt.isPresent()) {
            log.info("GET /api/pokemon/{} - 200 OK", id);
            return ResponseEntity.ok(opt.get());
        } else {
            log.warn("GET /api/pokemon/{} - 404 Not Found", id);
            return ResponseEntity.status(404).body(Map.of("error", "Pokemon não encontrado"));
        }
    }
}
