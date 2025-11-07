package com.ada.pokedex.service;

import com.ada.pokedex.dto.TrocaDTO;
import com.ada.pokedex.mapper.TrocaMapper;
import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.model.Treinador;
import com.ada.pokedex.model.Troca;
import com.ada.pokedex.repository.PokemonRepository;
import com.ada.pokedex.repository.TrocaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TrocaService {

    private final PokemonRepository pokemonRepo;
    private final TrocaRepository trocaRepo;
    private final TrocaMapper trocaMapper;

    // Injeção por construtor
    public TrocaService(PokemonRepository pokemonRepo, TrocaRepository trocaRepo, TrocaMapper trocaMapper) {
        this.pokemonRepo = pokemonRepo;
        this.trocaRepo = trocaRepo;
        this.trocaMapper = trocaMapper;
    }

    @Transactional
    public void trocarPokemon(Long id1, Long id2) {
        // 1. Trata erro 404
        Pokemon p1 = pokemonRepo.findById(id1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon com ID " + id1 + " não encontrado"));

        Pokemon p2 = pokemonRepo.findById(id2)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon com ID " + id2 + " não encontrado"));

        // 2. Validação de Regra de Negócio (Erro 400)
        Treinador t1 = p1.getTreinador();
        Treinador t2 = p2.getTreinador();

        if (t1 == null || t2 == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambos os Pokémons devem pertencer a um treinador para a troca.");
        }

        if (Objects.equals(t1.getId(), t2.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível trocar Pokémons com o mesmo treinador.");
        }

        // 3. Lógica da troca
        p1.setTreinador(t2);
        p2.setTreinador(t1);
        pokemonRepo.save(p1);
        pokemonRepo.save(p2);

        // 4. Registro do histórico
        Troca troca = new Troca();
        troca.setTreinador1(t1);
        troca.setTreinador2(t2);
        troca.setPokemon1(p1);
        troca.setPokemon2(p2);
        troca.setDataTroca(LocalDateTime.now());
        trocaRepo.save(troca);
    }

    public Page<TrocaDTO> listarTrocas(Pageable pageable) {
        // Chama .map() diretamente no objeto Page
        return trocaRepo.findAllWithDetails(pageable)
                .map(trocaMapper::toDTO);
    }

    public List<TrocaDTO> listarTrocas() {
        // 1. Chama o método paginado com "Pageable.unpaged()"
        // 2. Pega o conteúdo (a Lista) de dentro da Página
        return trocaRepo.findAllWithDetails(Pageable.unpaged())
                .getContent() // <-- Pega a List<Troca> de dentro da Page<Troca>
                .stream()
                .map(trocaMapper::toDTO)
                .collect(Collectors.toList());
    }
}