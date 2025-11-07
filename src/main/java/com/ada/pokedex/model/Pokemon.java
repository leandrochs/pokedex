package com.ada.pokedex.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int height;
    private int weight;

    @ElementCollection
    private List<String> abilities;

    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    @ElementCollection
    @CollectionTable(name = "pokemon_types")
    private List<String> types;

    @ElementCollection
    @CollectionTable(name = "pokemon_stats")
    @MapKeyColumn(name = "stat_name")
    @Column(name = "base_stat")
    private Map<String, Integer> stats = new HashMap<>();

    @Column(length = 1000)
    private String descricao;

    private String urlDoChoro;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }
    public Treinador getTreinador() { return treinador; }
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }
    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }
    public Map<String, Integer> getStats() { return stats; }
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getUrlDoChoro() { return urlDoChoro; }
    public void setUrlDoChoro(String urlDoChoro) { this.urlDoChoro = urlDoChoro; }
}