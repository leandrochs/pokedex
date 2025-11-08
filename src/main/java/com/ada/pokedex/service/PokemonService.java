package com.ada.pokedex.service;

import com.ada.pokedex.dto.PokemonDTO;
import com.ada.pokedex.dto.pokeapi.PokeApiFlavorTextEntry;
import com.ada.pokedex.dto.pokeapi.PokeApiPokemonDTO;
import com.ada.pokedex.dto.pokeapi.PokeApiSpeciesDTO;
import com.ada.pokedex.mapper.PokemonMapper;
import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.model.Treinador;
import com.ada.pokedex.repository.PokemonRepository;
import com.ada.pokedex.repository.TreinadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepo;
    private final TreinadorRepository treinadorRepo;
    private final PokemonMapper pokemonMapper;
    private final PokeApiService pokeApiService;
    private final PokedexService pokedexService; // Injeção para o "Modo Jogo"

    public PokemonService(PokemonRepository pokemonRepo,
                          TreinadorRepository treinadorRepo,
                          PokemonMapper pokemonMapper,
                          PokeApiService pokeApiService,
                          PokedexService pokedexService) {
        this.pokemonRepo = pokemonRepo;
        this.treinadorRepo = treinadorRepo;
        this.pokemonMapper = pokemonMapper;
        this.pokeApiService = pokeApiService;
        this.pokedexService = pokedexService;
    }
    /**
     * Lista Pokémons de forma paginada
     */
    public Page<PokemonDTO> listarTodos(Pageable pageable) {
        return pokemonRepo.findAllWithTreinador(pageable)
                .map(pokemonMapper::toDTO);
    }
    /**
     * Cria um novo Pokémon (Captura)
     */
    @Transactional
    public PokemonDTO criarPokemon(PokemonDTO dto) {
        Treinador t = findTreinadorById(dto.getTreinadorId());
        Pokemon p = pokemonMapper.toEntity(dto);
        p.setTreinador(t);
        Pokemon pSalvo = pokemonRepo.save(p);
        pokedexService.registrarCaptura(t.getId(), p.getName());
        return pokemonMapper.toDTO(pSalvo);
    }
    /**
     * Cria um lote de Pokémons (Captura em Lote)
     */
    @Transactional
    public List<PokemonDTO> criarPokemonEmLote(List<PokeApiPokemonDTO> apiDtos, Long treinadorId) {
        Treinador t = findTreinadorById(treinadorId);
        List<Pokemon> pokemonsParaSalvar = new ArrayList<>();
        for (PokeApiPokemonDTO apiDto : apiDtos) {
            Pokemon p = new Pokemon();
            p.setName(apiDto.getName());
            p.setHeight(apiDto.getHeight());
            p.setWeight(apiDto.getWeight());
            p.setAbilities(apiDto.getAbilities().stream()
                    .map(slot -> slot.getAbility().getName())
                    .collect(Collectors.toList()));
            p.setTreinador(t);
            pokemonsParaSalvar.add(p);
        }
        List<Pokemon> pokemonsSalvos = pokemonRepo.saveAll(pokemonsParaSalvar);
        for (Pokemon p : pokemonsSalvos) {
            pokedexService.registrarCaptura(treinadorId, p.getName());
        }
        return pokemonsSalvos.stream()
                .map(pokemonMapper::toDTO)
                .collect(Collectors.toList());
    }
    /**
     * Busca um único Pokémon pelo seu ID.
     */
    public PokemonDTO buscarPorId(Long id) {
        // Usa o método auxiliar com a query otimizada
        Pokemon pokemon = findPokemonById(id);
        return pokemonMapper.toDTO(pokemon);
    }
    /**
     * Atualiza um Pokémon existente.
     */
    @Transactional
    public PokemonDTO atualizarPokemon(Long id, PokemonDTO dto) {
        Pokemon p = findPokemonById(id);
        Treinador t = findTreinadorById(dto.getTreinadorId());
        p.setName(dto.getName());
        p.setHeight(dto.getHeight());
        p.setWeight(dto.getWeight());
        p.setAbilities(dto.getAbilities());
        p.setTreinador(t);
        p.setTypes(dto.getTypes());
        p.setStats(dto.getStats());
        p.setDescricao(dto.getDescricao());
        p.setUrlDoChoro(dto.getUrlDoChoro());

        Pokemon pSalvo = pokemonRepo.save(p);
        return pokemonMapper.toDTO(pSalvo);
    }
    /**
     * Deleta um Pokémon pelo seu ID.
     */
    @Transactional
    public void deletarPokemon(Long id) {
        if (!pokemonRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon com ID " + id + " não encontrado");
        }
        pokemonRepo.deleteById(id);
    }
    /**
     * Enriquece um Pokémon com dados da API externa
     */
    @Transactional
    public PokemonDTO enriquecerPokemon(Long id) {
        Pokemon p = findPokemonById(id);

        PokeApiPokemonDTO dadosPokemon = pokeApiService.importarPokemon(p.getName());
        PokeApiSpeciesDTO dadosSpecies = pokeApiService.getSpecies(p.getName());
        List<String> tipos = dadosPokemon.getTypes().stream()
                .map(typeSlot -> typeSlot.getType().getName())
                .collect(Collectors.toList());
        Map<String, Integer> statsMap = dadosPokemon.getStats().stream()
                .collect(Collectors.toMap(
                        statSlot -> statSlot.getStat().getName(),
                        statSlot -> statSlot.getBaseStat()
                ));
        String urlDoChoro = (dadosPokemon.getCries() != null) ? dadosPokemon.getCries().getLatest() : null;
        String descricao = dadosSpecies.getFlavorTextEntries().stream()
                .filter(entry -> entry.getLanguage().getName().equals("en"))
                .map(PokeApiFlavorTextEntry::getFlavorText)
                .findFirst()
                .orElse("No description found.")
                .replaceAll("[\n\f]", " ");
        p.setTypes(tipos);
        p.setStats(statsMap);
        p.setUrlDoChoro(urlDoChoro);
        p.setDescricao(descricao);
        Pokemon pSalvo = pokemonRepo.save(p);
        return pokemonMapper.toDTO(pSalvo);
    }
    /**
     * Auxiliar para buscar a ENTIDADE Treinador ou lançar 404.
     */
    private Treinador findTreinadorById(Long id) {
        return treinadorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treinador com ID " + id + " não encontrado"));
    }
    /**
     * Auxiliar para buscar a ENTIDADE Pokémon (com treinador) ou lançar 404.
     */
    private Pokemon findPokemonById(Long id) {
        return pokemonRepo.findByIdWithTreinador(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pokémon com ID " + id + " não encontrado"));
    }
}