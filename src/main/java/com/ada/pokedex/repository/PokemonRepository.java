package com.ada.pokedex.repository;

import com.ada.pokedex.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    /**
     * Busca uma página de Pokémons, já incluindo os Treinadores associados
     */
    @Query(value = "SELECT p FROM Pokemon p LEFT JOIN FETCH p.treinador",
            countQuery = "SELECT count(p) FROM Pokemon p")
    Page<Pokemon> findAllWithTreinador(Pageable pageable);

    /**
     * Busca um Pokémon específico por ID, já incluindo o Treinador associado.
     */
    @Query("SELECT p FROM Pokemon p LEFT JOIN FETCH p.treinador WHERE p.id = :id")
    Optional<Pokemon> findByIdWithTreinador(Long id);
}
