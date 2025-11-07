package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiAbility {

    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}