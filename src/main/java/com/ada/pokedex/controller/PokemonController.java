package com.ada.pokedex.controller;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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



}
