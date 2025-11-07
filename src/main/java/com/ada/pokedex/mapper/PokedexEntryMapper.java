package com.ada.pokedex.mapper;

import com.ada.pokedex.dto.PokedexEntryDTO;
import com.ada.pokedex.model.PokedexEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PokedexEntryMapper {

    @Mapping(source = "treinador.id", target = "treinadorId")
    PokedexEntryDTO toDTO(PokedexEntry entry);
}