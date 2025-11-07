package com.ada.pokedex.repository;

import com.ada.pokedex.model.Treinador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinadorRepository extends JpaRepository<Treinador, Long> {}

