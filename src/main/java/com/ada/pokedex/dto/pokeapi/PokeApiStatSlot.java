package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiStatSlot {

    @JsonProperty("base_stat")
    private int baseStat;

    private PokeApiNameUrlDTO stat;

    public int getBaseStat() { return baseStat; }
    public void setBaseStat(int baseStat) { this.baseStat = baseStat; }
    public PokeApiNameUrlDTO getStat() { return stat; }
    public void setStat(PokeApiNameUrlDTO stat) { this.stat = stat; }
}