package com.ada.pokedex.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiCriesDTO {

    private String latest;
    private String legacy;

    public String getLatest() { return latest; }
    public void setLatest(String latest) { this.latest = latest; }
    public String getLegacy() { return legacy; }
    public void setLegacy(String legacy) { this.legacy = legacy; }
}