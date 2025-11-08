package com.ada.pokedex.dto;

import java.time.LocalDateTime;

public class TrocaDTO {
    private Long id;
    private Long treinador1Id;
    private Long treinador2Id;
    private Long pokemon1Id;
    private Long pokemon2Id;
    private LocalDateTime dataTroca;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTreinador1Id() { return treinador1Id; }
    public void setTreinador1Id(Long treinador1Id) { this.treinador1Id = treinador1Id; }
    public Long getTreinador2Id() { return treinador2Id; }
    public void setTreinador2Id(Long treinador2Id) { this.treinador2Id = treinador2Id; }
    public Long getPokemon1Id() { return pokemon1Id; }
    public void setPokemon1Id(Long pokemon1Id) { this.pokemon1Id = pokemon1Id; }
    public Long getPokemon2Id() { return pokemon2Id; }
    public void setPokemon2Id(Long pokemon2Id) { this.pokemon2Id = pokemon2Id; }
    public LocalDateTime getDataTroca() { return dataTroca; }
    public void setDataTroca(LocalDateTime dataTroca) { this.dataTroca = dataTroca; }
}