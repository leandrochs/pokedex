package com.ada.pokedex.controller;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
