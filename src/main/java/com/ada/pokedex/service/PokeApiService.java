package com.ada.pokedex.service;

import com.ada.pokedex.dto.pokeapi.*;
import com.ada.pokedex.dto.pokeapi.PokeApiEvolutionChainDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PokeApiService {

    private final RestTemplate restTemplate;
    private final String API_URL_POKEMON = "https://pokeapi.co/api/v2/pokemon/";
    private final String API_URL_SPECIES = "https://pokeapi.co/api/v2/pokemon-species/";

    public PokeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    /**
     * Busca os detalhes de um Pokémon pelo nome.
     */
    public PokeApiPokemonDTO importarPokemon(String nome) {
        try {
            String url = API_URL_POKEMON + nome.toLowerCase();
            return restTemplate.getForObject(url, PokeApiPokemonDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pokémon '" + nome + "' não encontrado na PokéAPI externa.");
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar dados da PokéAPI: " + ex.getMessage());
        }
    }
    /**
     * Busca a lista de Pokémons com paginação.
     */
    public List<PokeApiNameUrlDTO> listarPokemons(int limit, int offset) {
        try {
            String url = String.format("%s?limit=%d&offset=%d", API_URL_POKEMON, limit, offset);
            PokeApiListResponseDTO response = restTemplate.getForObject(url, PokeApiListResponseDTO.class);
            if (response == null || response.getResults() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resposta inválida da PokéAPI ao listar.");
            }
            return response.getResults();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar Pokémons da PokéAPI: " + ex.getMessage());
        }
    }
    /**
     * Busca os locais (habitats) onde um Pokémon pode ser encontrado.
     */
    public PokeApiEncounterDTO[] getEncounters(String nome) {
        String url = API_URL_POKEMON + nome.toLowerCase() + "/encounters";

        try {
            PokeApiEncounterDTO[] response = restTemplate.getForObject(url, PokeApiEncounterDTO[].class);
            if (response == null) {
                return new PokeApiEncounterDTO[0];
            }
            return response;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon '" + nome + "' não encontrado na PokéAPI.");
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar habitats da PokéAPI: " + ex.getMessage());
        }
    }
    /**
     * Busca os dados da "espécie" (descrição, etc.).
     */
    public PokeApiSpeciesDTO getSpecies(String nome) {
        try {
            String url = API_URL_SPECIES + nome.toLowerCase();
            return restTemplate.getForObject(url, PokeApiSpeciesDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Espécie do Pokémon '" + nome + "' não encontrada.");
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar dados da espécie da PokéAPI: " + ex.getMessage());
        }
    }
    /**
     * Busca uma Cadeia de Evolução completa a partir de sua URL.
     */
    public PokeApiEvolutionChainDTO getEvolutionChain(String chainUrl) {
        if (chainUrl == null || chainUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL da cadeia de evolução inválida.");
        }
        try {
            return restTemplate.getForObject(chainUrl, PokeApiEvolutionChainDTO.class);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar cadeia de evolução da PokéAPI: " + ex.getMessage());
        }
    }
}