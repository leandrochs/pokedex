package com.ada.pokedex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistrarVistoDTO {

    @NotNull
    private Long treinadorId;

    @NotBlank
    private String pokemonName;

    // Getters e Setters
    public Long getTreinadorId() { return treinadorId; }
    public void setTreinadorId(Long treinadorId) { this.treinadorId = treinadorId; }
    public String getPokemonName() { return pokemonName; }
    public void setPokemonName(String pokemonName) { this.pokemonName = pokemonName; }
}