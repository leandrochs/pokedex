package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiFlavorTextEntry {

    @JsonProperty("flavor_text")
    private String flavorText;

    private PokeApiNameUrlDTO language; // Reutiliza o DTO que já temos

    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }
    public PokeApiNameUrlDTO getLanguage() { return language; }
    public void setLanguage(PokeApiNameUrlDTO language) { this.language = language; }
}
