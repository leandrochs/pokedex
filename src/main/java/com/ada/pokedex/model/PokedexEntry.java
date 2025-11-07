package com.ada.pokedex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pokedex_entries", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"treinador_id", "pokemonName"})
})
public class PokedexEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    @Column(nullable = false)
    private String pokemonName;

    private Integer pokemonSpeciesId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PokedexStatus status;

    private LocalDateTime dataVisto;
    private LocalDateTime dataCaptura;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Treinador getTreinador() { return treinador; }
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }
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