package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ada.pokedex.dto.TrocaDTO;
import com.ada.pokedex.service.TrocaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Trocas", description = "Troca de Pokémons entre treinadores")
@RestController
@RequestMapping("/api/troca")
@SecurityRequirement(name = "bearerAuth")
public class TrocaController {

    private final TrocaService trocaService;
    public TrocaController(TrocaService trocaService) {
        this.trocaService = trocaService;
    }

    @Operation(summary = "Realiza troca entre dois Pokémons")
    @PostMapping
    public ResponseEntity<String> trocar(@RequestParam Long pokemon1, @RequestParam Long pokemon2) {
        trocaService.trocarPokemon(pokemon1, pokemon2);
        return ResponseEntity.ok("Pokémons trocados com sucesso!");
    }

    @Operation(summary = "Lista o histórico de trocas de forma paginada")
    @GetMapping("/historico")
    public Page<TrocaDTO> listarHistorico(Pageable pageable) {

        return trocaService.listarTrocas(pageable);
    }

}