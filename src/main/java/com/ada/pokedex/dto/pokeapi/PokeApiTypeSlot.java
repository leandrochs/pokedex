package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiTypeSlot {

    private PokeApiNameUrlDTO type;

    public PokeApiNameUrlDTO getType() { return type; }
    public void setType(PokeApiNameUrlDTO type) { this.type = type; }
}