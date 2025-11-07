package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiAbilitySlot {

    private PokeApiAbility ability;

    public PokeApiAbility getAbility() { return ability; }
    public void setAbility(PokeApiAbility ability) { this.ability = ability; }
}