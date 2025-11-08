package com.ada.pokedex.mapper;

import com.ada.pokedex.dto.PokemonDTO;
import com.ada.pokedex.model.Pokemon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
    @Mapping(source = "treinador.id", target = "treinadorId")
    PokemonDTO toDTO(Pokemon pokemon);

    @Mapping(target = "treinador", ignore = true)
    Pokemon toEntity(PokemonDTO dto);
}