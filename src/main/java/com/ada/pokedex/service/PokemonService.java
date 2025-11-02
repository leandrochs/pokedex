package com.ada.pokedex.service;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;


    public List<Pokemon> getAll() {
        System.out.println("Olá da Service");
        return pokemonRepository.findAll();
    }



}
