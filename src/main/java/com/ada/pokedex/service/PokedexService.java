package com.ada.pokedex.service;

import com.ada.pokedex.dto.PokedexEntryDTO;
import com.ada.pokedex.dto.RegistrarVistoDTO;
import com.ada.pokedex.dto.pokeapi.PokeApiSpeciesDTO;
import com.ada.pokedex.mapper.PokedexEntryMapper;
import com.ada.pokedex.model.PokedexEntry;
import com.ada.pokedex.model.PokedexStatus;
import com.ada.pokedex.model.Treinador;
import com.ada.pokedex.repository.PokedexEntryRepository;
import com.ada.pokedex.repository.TreinadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PokedexService {

    private final PokedexEntryRepository entryRepo;
    private final TreinadorRepository treinadorRepo;
    private final PokeApiService pokeApiService;
    private final PokedexEntryMapper entryMapper;

    public PokedexService(PokedexEntryRepository entryRepo,
                          TreinadorRepository treinadorRepo,
                          PokeApiService pokeApiService,
                          PokedexEntryMapper entryMapper) {
        this.entryRepo = entryRepo;
        this.treinadorRepo = treinadorRepo;
        this.pokeApiService = pokeApiService;
        this.entryMapper = entryMapper;
    }
    /**
     * Registra que um treinador VIU um Pokémon.
     */
    @Transactional
    public PokedexEntryDTO registrarVisao(RegistrarVistoDTO dto) {
        Treinador treinador = findTreinadorById(dto.getTreinadorId());
        Optional<PokedexEntry> entryOpt = entryRepo.findByTreinadorAndPokemonName(treinador, dto.getPokemonName());

        if (entryOpt.isPresent()) {
            return entryMapper.toDTO(entryOpt.get());
        }
        PokeApiSpeciesDTO speciesData = pokeApiService.getSpecies(dto.getPokemonName());
        PokedexEntry novaEntrada = new PokedexEntry();
        novaEntrada.setTreinador(treinador);
        novaEntrada.setPokemonName(dto.getPokemonName());
        novaEntrada.setPokemonSpeciesId(speciesData.getId());
        novaEntrada.setStatus(PokedexStatus.VISTO);
        novaEntrada.setDataVisto(LocalDateTime.now());

        PokedexEntry entradaSalva = entryRepo.save(novaEntrada);
        return entryMapper.toDTO(entradaSalva);
    }
    /**
     * Registra que um treinador CAPTUROU um Pokémon.
     */
    @Transactional
    public void registrarCaptura(Long treinadorId, String pokemonName) {
        Treinador treinador = findTreinadorById(treinadorId);

        Optional<PokedexEntry> entryOpt = entryRepo.findByTreinadorAndPokemonName(treinador, pokemonName);

        if (entryOpt.isEmpty()) {
            PokeApiSpeciesDTO speciesData = pokeApiService.getSpecies(pokemonName);
            PokedexEntry novaEntrada = new PokedexEntry();
            novaEntrada.setTreinador(treinador);
            novaEntrada.setPokemonName(pokemonName);
            novaEntrada.setPokemonSpeciesId(speciesData.getId());
            novaEntrada.setStatus(PokedexStatus.CAPTURADO);
            novaEntrada.setDataCaptura(LocalDateTime.now());
            entryRepo.save(novaEntrada);

        } else {
            PokedexEntry entradaExistente = entryOpt.get();
            if (entradaExistente.getStatus() == PokedexStatus.VISTO) {
                entradaExistente.setStatus(PokedexStatus.CAPTURADO);
                entradaExistente.setDataCaptura(LocalDateTime.now());
                entryRepo.save(entradaExistente);
            }
        }
    }

    /**
     * Lista todas as entradas da Pokédex de um treinador.
     */
    public Page<PokedexEntryDTO> getEntriesByTreinador(Long treinadorId, Pageable pageable) {
        Treinador treinador = findTreinadorById(treinadorId);
        return entryRepo.findByTreinador(treinador, pageable)
                .map(entryMapper::toDTO);
    }
    private Treinador findTreinadorById(Long id) {
        return treinadorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treinador com ID " + id + " não encontrado"));
    }
}