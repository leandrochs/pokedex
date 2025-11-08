package com.ada.pokedex.dto;

import com.ada.pokedex.model.PokedexStatus;
import java.time.LocalDateTime;

public class PokedexEntryDTO {

    private Long id;
    private Long treinadorId;
    private String pokemonName;
    private Integer pokemonSpeciesId;
    private PokedexStatus status;
    private LocalDateTime dataVisto;
    private LocalDateTime dataCaptura;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTreinadorId() { return treinadorId; }
    public void setTreinadorId(Long treinadorId) { this.treinadorId = treinadorId; }
    public String getPokemonName() { return pokemonName; }
    public void setPokemonName(String pokemonName) { this.pokemonName = pokemonName; }
    public Integer getPokemonSpeciesId() { return pokemonSpeciesId; }
    public void setPokemonSpeciesId(Integer pokemonSpeciesId) { this.pokemonSpeciesId = pokemonSpeciesId; }
    public PokedexStatus getStatus() { return status; }
    public void setStatus(PokedexStatus status) { this.status = status; }
    public LocalDateTime getDataVisto() { return dataVisto; }
    public void setDataVisto(LocalDateTime dataVisto) { this.dataVisto = dataVisto; }
    public LocalDateTime getDataCaptura() { return dataCaptura; }
    public void setDataCaptura(LocalDateTime dataCaptura) { this.dataCaptura = dataCaptura; }
}