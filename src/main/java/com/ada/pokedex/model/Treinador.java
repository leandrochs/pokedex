package com.ada.pokedex.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Treinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int idade;
    private String cidade;

    @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL)
    private List<Pokemon> pokemons = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public List<Pokemon> getPokemons() { return pokemons; }
    public void setPokemons(List<Pokemon> pokemons) { this.pokemons = pokemons; }
}