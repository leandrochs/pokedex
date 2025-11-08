package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiPokemonDTO {

    private String name;
    private int height;
    private int weight;
    private List<PokeApiAbilitySlot> abilities;
    private List<PokeApiTypeSlot> types;
    private List<PokeApiStatSlot> stats;
    private PokeApiCriesDTO cries;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public List<PokeApiAbilitySlot> getAbilities() { return abilities; }
    public void setAbilities(List<PokeApiAbilitySlot> abilities) { this.abilities = abilities; }
    public List<PokeApiTypeSlot> getTypes() { return types; }
    public void setTypes(List<PokeApiTypeSlot> types) { this.types = types; }
    public List<PokeApiStatSlot> getStats() { return stats; }
    public void setStats(List<PokeApiStatSlot> stats) { this.stats = stats; }
    public PokeApiCriesDTO getCries() { return cries; }
    public void setCries(PokeApiCriesDTO cries) { this.cries = cries; }
}