package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ada.pokedex.dto.pokeapi.*;
import com.ada.pokedex.service.PokeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Consulta Externa", description = "Consultar a PokéAPI (Modo Pokedex)")
@RestController
@RequestMapping("/api/pokedex-externa")
public class ExternalPokedexController {

    private final PokeApiService pokeApiService;

    public ExternalPokedexController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @Operation(summary = "Busca dados de um Pokémon na API externa")
    @GetMapping("/{nome}")
    public ResponseEntity<PokeApiPokemonDTO> consultarPokemonExterno(@PathVariable String nome) {
        PokeApiPokemonDTO dadosExternos = pokeApiService.importarPokemon(nome);
        return ResponseEntity.ok(dadosExternos);
    }
    @Operation(summary = "Busca os habitats (locais de encontro) de um Pokémon")
    @GetMapping("/{nome}/habitat")
    public ResponseEntity<List<String>> consultarHabitat(@PathVariable String nome) {
        PokeApiEncounterDTO[] encounters = pokeApiService.getEncounters(nome);
        List<String> locationNames = Arrays.stream(encounters)
                .map(encounter -> encounter.getLocationArea().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationNames);
    }
    @Operation(summary = "Busca a cadeia de evolução de um Pokémon")
    @GetMapping("/{nome}/evolucao")
    public ResponseEntity<List<String>> consultarEvolucao(@PathVariable String nome) {
        PokeApiSpeciesDTO speciesData = pokeApiService.getSpecies(nome);
        String evolutionUrl = speciesData.getEvolutionChain().getUrl();
        PokeApiEvolutionChainDTO chainData = pokeApiService.getEvolutionChain(evolutionUrl);
        List<String> evolutionNames = new ArrayList<>();
        parseEvolutionChain(chainData.getChain(), evolutionNames);
        return ResponseEntity.ok(evolutionNames);
    }
    private void parseEvolutionChain(PokeApiChainLinkDTO chainLink, List<String> names) {
        if (chainLink == null) {
            return;
        }
        names.add(chainLink.getSpecies().getName());
        if (chainLink.getEvolvesTo() == null || chainLink.getEvolvesTo().isEmpty()) {
            return;
        }
        for (PokeApiChainLinkDTO nextLink : chainLink.getEvolvesTo()) {
            parseEvolutionChain(nextLink, names);
        }
    }
}