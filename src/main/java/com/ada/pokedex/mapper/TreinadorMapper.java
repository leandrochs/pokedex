package com.ada.pokedex.mapper;

import com.ada.pokedex.dto.TreinadorDTO;
import com.ada.pokedex.model.Treinador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreinadorMapper {

    TreinadorDTO toDTO(Treinador treinador);

    @Mapping(target = "pokemons", ignore = true)
    Treinador toEntity(TreinadorDTO dto);
}