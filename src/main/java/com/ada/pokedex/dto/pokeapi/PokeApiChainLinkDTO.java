package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiChainLinkDTO {

    private PokeApiNameUrlDTO species;

    @JsonProperty("evolves_to")
    private List<PokeApiChainLinkDTO> evolvesTo;

    public PokeApiNameUrlDTO getSpecies() { return species; }
    public void setSpecies(PokeApiNameUrlDTO species) { this.species = species; }
    public List<PokeApiChainLinkDTO> getEvolvesTo() { return evolvesTo; }
    public void setEvolvesTo(List<PokeApiChainLinkDTO> evolvesTo) { this.evolvesTo = evolvesTo; }
}