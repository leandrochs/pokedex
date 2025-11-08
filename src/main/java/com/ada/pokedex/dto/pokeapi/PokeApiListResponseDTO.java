package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiListResponseDTO {

    private List<PokeApiNameUrlDTO> results;

    public List<PokeApiNameUrlDTO> getResults() { return results; }
    public void setResults(List<PokeApiNameUrlDTO> results) { this.results = results; }
}