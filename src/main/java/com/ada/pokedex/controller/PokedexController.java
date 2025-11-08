package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.ada.pokedex.dto.PokedexEntryDTO;
import com.ada.pokedex.dto.RegistrarVistoDTO;
import com.ada.pokedex.service.PokedexService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Pokédex", description = "Gerenciamento de entradas 'Visto' vs 'Capturado'")
@RestController
@RequestMapping("/api/pokedex-entries")
@SecurityRequirement(name = "bearerAuth")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @Operation(summary = "Lista as entradas da Pokédex de um treinador (paginado)")
    @GetMapping("/treinador/{treinadorId}")
    public Page<PokedexEntryDTO> getPokedexDoTreinador(@PathVariable Long treinadorId, Pageable pageable) {
        return pokedexService.getEntriesByTreinador(treinadorId, pageable);
    }

    @Operation(summary = "Registra que um treinador 'VIU' um Pokémon")
    @PostMapping("/ver")
    public ResponseEntity<PokedexEntryDTO> registrarVisao(@Valid @RequestBody RegistrarVistoDTO dto) {
        PokedexEntryDTO entry = pokedexService.registrarVisao(dto);

        // Retorna 200 OK se a entrada já existia, ou 201 Created se foi nova
        if(entry.getDataVisto().isBefore(java.time.LocalDateTime.now().minusSeconds(2))) {
            return ResponseEntity.ok(entry);
        } else {
            URI location = URI.create("/api/pokedex-entries/" + entry.getId());
            return ResponseEntity.created(location).body(entry);
        }
    }
}