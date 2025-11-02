package com.ada.pokedex.repository;

import com.ada.pokedex.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {



}
