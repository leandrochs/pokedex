package com.ada.pokedex.repository;

import com.ada.pokedex.model.PokedexEntry;
import com.ada.pokedex.model.Treinador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokedexEntryRepository extends JpaRepository<PokedexEntry, Long> {

    Optional<PokedexEntry> findByTreinadorAndPokemonName(Treinador treinador, String pokemonName);

    Page<PokedexEntry> findByTreinador(Treinador treinador, Pageable pageable);
}