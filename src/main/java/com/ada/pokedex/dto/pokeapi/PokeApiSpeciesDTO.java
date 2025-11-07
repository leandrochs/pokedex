package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiSpeciesDTO {

    private int id;

    @JsonProperty("flavor_text_entries")
    private List<PokeApiFlavorTextEntry> flavorTextEntries;

    @JsonProperty("evolution_chain")
    private PokeApiNameUrlDTO evolutionChain;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<PokeApiFlavorTextEntry> getFlavorTextEntries() { return flavorTextEntries; }
    public void setFlavorTextEntries(List<PokeApiFlavorTextEntry> flavorTextEntries) { this.flavorTextEntries = flavorTextEntries; }
    public PokeApiNameUrlDTO getEvolutionChain() { return evolutionChain; }
    public void setEvolutionChain(PokeApiNameUrlDTO evolutionChain) { this.evolutionChain = evolutionChain; }
}