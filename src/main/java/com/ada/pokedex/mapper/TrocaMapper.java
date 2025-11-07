package com.ada.pokedex.mapper;

import com.ada.pokedex.dto.TrocaDTO;
import com.ada.pokedex.model.Troca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrocaMapper {

    @Mapping(source = "treinador1.id", target = "treinador1Id")
    @Mapping(source = "treinador2.id", target = "treinador2Id")
    @Mapping(source = "pokemon1.id", target = "pokemon1Id")
    @Mapping(source = "pokemon2.id", target = "pokemon2Id")
    TrocaDTO toDTO(Troca troca);
}