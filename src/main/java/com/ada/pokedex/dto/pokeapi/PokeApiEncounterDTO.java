package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiEncounterDTO {

    @JsonProperty("location_area")
    private PokeApiNameUrlDTO locationArea;

    public PokeApiNameUrlDTO getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(PokeApiNameUrlDTO locationArea) {
        this.locationArea = locationArea;
    }
}