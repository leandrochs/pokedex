package com.ada.pokedex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.ada.pokedex.dto.TreinadorDTO;
import com.ada.pokedex.service.TreinadorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Treinadores", description = "Gerenciamento de treinadores")
@RestController
@RequestMapping("/api/treinadores")
@SecurityRequirement(name = "bearerAuth")
public class TreinadorController {

    private final TreinadorService treinadorService;
    public TreinadorController(TreinadorService treinadorService) {
        this.treinadorService = treinadorService;
    }

    @Operation(summary = "Lista todos os treinadores de forma paginada")
    @GetMapping
    public Page<TreinadorDTO> listar(Pageable pageable) {
        return treinadorService.listarTodos(pageable);
    }
    @Operation(summary = "Cria um novo treinador")
    @PostMapping
    public ResponseEntity<TreinadorDTO> criar(@Valid @RequestBody TreinadorDTO dto) {
        TreinadorDTO treinadorCriado = treinadorService.criarTreinador(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(treinadorCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(treinadorCriado);
    }
    @Operation(summary = "Busca um treinador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TreinadorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(treinadorService.buscarPorId(id));
    }
    @Operation(summary = "Atualiza um treinador existente")
    @PutMapping("/{id}")
    public ResponseEntity<TreinadorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TreinadorDTO dto) {
        TreinadorDTO treinadorAtualizado = treinadorService.atualizarTreinador(id, dto);
        return ResponseEntity.ok(treinadorAtualizado);
    }
    @Operation(summary = "Deleta um treinador")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        treinadorService.deletarTreinador(id);
    }
}