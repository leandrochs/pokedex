package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiEvolutionChainDTO {

    private PokeApiChainLinkDTO chain;

    public PokeApiChainLinkDTO getChain() { return chain; }
    public void setChain(PokeApiChainLinkDTO chain) { this.chain = chain; }
}