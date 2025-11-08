package com.ada.pokedex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Troca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Treinador treinador1;
    @ManyToOne private Treinador treinador2;
    @ManyToOne private Pokemon pokemon1;
    @ManyToOne private Pokemon pokemon2;

    private LocalDateTime dataTroca;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Treinador getTreinador1() { return treinador1; }
    public void setTreinador1(Treinador treinador1) { this.treinador1 = treinador1; }

    public Treinador getTreinador2() { return treinador2; }
    public void setTreinador2(Treinador treinador2) { this.treinador2 = treinador2; }

    public Pokemon getPokemon1() { return pokemon1; }
    public void setPokemon1(Pokemon pokemon1) { this.pokemon1 = pokemon1; }

    public Pokemon getPokemon2() { return pokemon2; }
    public void setPokemon2(Pokemon pokemon2) { this.pokemon2 = pokemon2; }

    public LocalDateTime getDataTroca() { return dataTroca; }
    public void setDataTroca(LocalDateTime dataTroca) { this.dataTroca = dataTroca; }
}