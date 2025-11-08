package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ada.pokedex.dto.PokemonDTO;
import com.ada.pokedex.dto.pokeapi.PokeApiNameUrlDTO;
import com.ada.pokedex.dto.pokeapi.PokeApiPokemonDTO;
import com.ada.pokedex.service.PokeApiService;
import com.ada.pokedex.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Importação", description = "Importar Pokémons da PokéAPI externa")
@RestController
@RequestMapping("/api/import")
@SecurityRequirement(name = "bearerAuth")
public class PokemonImportController {

    private final PokeApiService pokeApiService;
    private final PokemonService pokemonService;

    public PokemonImportController(PokeApiService pokeApiService, PokemonService pokemonService) {
        this.pokeApiService = pokeApiService;
        this.pokemonService = pokemonService;
    }
    @Operation(summary = "Importa um Pokémon específico e o salva no banco local")
    @PostMapping("/pokemon")
    public ResponseEntity<PokemonDTO> importarEAdicionar(
            @RequestParam String nome,
            @RequestParam Long treinadorId) {
        PokeApiPokemonDTO apiDto = pokeApiService.importarPokemon(nome);
        PokemonDTO localDto = new PokemonDTO();
        localDto.setName(apiDto.getName());
        localDto.setHeight(apiDto.getHeight());
        localDto.setWeight(apiDto.getWeight());
        localDto.setTreinadorId(treinadorId);
        localDto.setAbilities(apiDto.getAbilities().stream()
                .map(abilitySlot -> abilitySlot.getAbility().getName())
                .collect(Collectors.toList()));
        PokemonDTO pokemonCriado = pokemonService.criarPokemon(localDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().replacePath("/api/pokedex/{id}")
                .buildAndExpand(pokemonCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(pokemonCriado);
    }
    /**
     * Importa um lote de Pokémons e salva no banco local
     */
    @Operation(summary = "Importa um lote de Pokémons (ex: os 10 primeiros) e os salva")
    @PostMapping("/batch")
    public ResponseEntity<List<PokemonDTO>> importarLote(
            @RequestParam Long treinadorId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<PokeApiNameUrlDTO> pokemonsParaImportar = pokeApiService.listarPokemons(limit, offset);
        List<PokeApiPokemonDTO> dtosDaApi = new ArrayList<>();
        for (PokeApiNameUrlDTO pokeRef : pokemonsParaImportar) {
            PokeApiPokemonDTO detalhes = pokeApiService.importarPokemon(pokeRef.getName());
            dtosDaApi.add(detalhes);
        }
        List<PokemonDTO> pokemonsSalvos = pokemonService.criarPokemonEmLote(dtosDaApi, treinadorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonsSalvos);
    }
}