package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import com.ada.pokedex.dto.PokemonDTO;
import com.ada.pokedex.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Pokémons", description = "Gerenciamento de Pokémons")
@RestController
@RequestMapping("/api/pokedex")
@SecurityRequirement(name = "bearerAuth")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Operation(summary = "Lista todos os Pokémons de forma paginada")
    @GetMapping
    public Page<PokemonDTO> listar(Pageable pageable) {

        return pokemonService.listarTodos(pageable);
    }

    @Operation(summary = "Cria um novo Pokémon")
    @PostMapping
    public ResponseEntity<PokemonDTO> criar(@Valid @RequestBody PokemonDTO dto) {
        PokemonDTO pokemonCriado = pokemonService.criarPokemon(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pokemonCriado.getId())
                .toUri();

        return ResponseEntity.created(location).body(pokemonCriado);
    }

    @Operation(summary = "Enriquece um Pokémon com dados da API externa (Tipos, Stats)")
    @PostMapping("/{id}/enriquecer")
    public ResponseEntity<PokemonDTO> enriquecer(@PathVariable Long id) {
        PokemonDTO pokemonEnriquecido = pokemonService.enriquecerPokemon(id);
        return ResponseEntity.ok(pokemonEnriquecido);
    }

    @Operation(summary = "Busca um Pokémon por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PokemonDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pokemonService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza um Pokémon existente")
    @PutMapping("/{id}")
    public ResponseEntity<PokemonDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PokemonDTO dto) {
        PokemonDTO pokemonAtualizado = pokemonService.atualizarPokemon(id, dto);
        return ResponseEntity.ok(pokemonAtualizado);
    }

    @Operation(summary = "Deleta um Pokémon")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        pokemonService.deletarPokemon(id);
    }
}
