package com.ada.pokedex.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public class PokemonDTO {
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @Min(value = 1, message = "A altura deve ser no mínimo 1")
    private int height;

    @Min(value = 1, message = "O peso deve ser no mínimo 1")
    private int weight;

    @NotEmpty(message = "O Pokémon deve ter pelo menos uma habilidade")
    private List<String> abilities;

    @NotNull(message = "O ID do treinador é obrigatório")
    private Long treinadorId;

    private List<String> types;
    private Map<String, Integer> stats;
    private String descricao;
    private String urlDoChoro;

    // Getters e Setters
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
    public Long getTreinadorId() { return treinadorId; }
    public void setTreinadorId(Long treinadorId) { this.treinadorId = treinadorId; }
    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }
    public Map<String, Integer> getStats() { return stats; }
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getUrlDoChoro() { return urlDoChoro; }
    public void setUrlDoChoro(String urlDoChoro) { this.urlDoChoro = urlDoChoro; }
}