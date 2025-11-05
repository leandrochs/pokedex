package com.ada.pokedex.controller;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/api/pokemon")
@RestController
public class PokemonController {

    private final PokemonService pokemonService;


    @GetMapping("/teste")
    public ResponseEntity<List<Pokemon>> getAll() {
        System.out.println("Olá da controller");
        List<Pokemon> result = pokemonService.getAll();
        return ResponseEntity.ok(result);
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
