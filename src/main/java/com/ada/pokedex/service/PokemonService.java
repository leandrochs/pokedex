package com.ada.pokedex.service;

import com.ada.pokedex.model.Pokemon;
import com.ada.pokedex.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    // Métod o genérico para atualizar qualquer tipo de campo
    @SuppressWarnings("unchecked")
    private <T> void atualizarDados(T novoValor, Consumer<T> setter) {
        if (novoValor == null) return;
        if (novoValor instanceof String valorString) {
            if (!valorString.isBlank()) {
                setter.accept((T) valorString);
            }
        } else {
            setter.accept(novoValor);
        }
    }

    // AtualizaçãoPokemon
    public Optional<Pokemon> atualizarPokemon(Long id, Pokemon dadosAtualizados) {
        Optional<Pokemon> idValido = pokemonRepository.findById(id);

        if (idValido.isPresent()) {
            Pokemon pokemon = idValido.get();

            atualizarDados(dadosAtualizados.getName(), pokemon::setName);
            atualizarDados(dadosAtualizados.getDescricao(), pokemon::setDescricao);
            atualizarDados(dadosAtualizados.getTipo(), pokemon::setTipo);
            atualizarDados(dadosAtualizados.getNivel(), pokemon::setNivel);

            pokemonRepository.save(pokemon);
            return Optional.of(pokemon);
        }

        return Optional.empty();
    }
}