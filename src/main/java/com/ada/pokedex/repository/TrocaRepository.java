package com.ada.pokedex.repository;

import com.ada.pokedex.model.Troca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrocaRepository extends JpaRepository<Troca, Long> {

    @Query(value = "SELECT t FROM Troca t " +
            "LEFT JOIN FETCH t.treinador1 " +
            "LEFT JOIN FETCH t.treinador2 " +
            "LEFT JOIN FETCH t.pokemon1 " +
            "LEFT JOIN FETCH t.pokemon2",
            countQuery = "SELECT count(t) FROM Troca t")
    Page<Troca> findAllWithDetails(Pageable pageable);
}