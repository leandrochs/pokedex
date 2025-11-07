package com.ada.pokedex.service;

import com.ada.pokedex.dto.TreinadorDTO;
import com.ada.pokedex.mapper.TreinadorMapper;
import com.ada.pokedex.model.Treinador;
import com.ada.pokedex.repository.TreinadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TreinadorService {

    private final TreinadorRepository treinadorRepo;
    private final TreinadorMapper treinadorMapper;

    public TreinadorService(TreinadorRepository treinadorRepo, TreinadorMapper treinadorMapper) {
        this.treinadorRepo = treinadorRepo;
        this.treinadorMapper = treinadorMapper;
    }

    public Page<TreinadorDTO> listarTodos(Pageable pageable) {
        return treinadorRepo.findAll(pageable)
                .map(treinadorMapper::toDTO);
    }

    @Transactional
    public TreinadorDTO criarTreinador(TreinadorDTO dto) {
        Treinador t = treinadorMapper.toEntity(dto);
        Treinador tSalvo = treinadorRepo.save(t);
        return treinadorMapper.toDTO(tSalvo);
    }

    public TreinadorDTO buscarPorId(Long id) {
        Treinador t = findTreinadorById(id);
        return treinadorMapper.toDTO(t);
    }

    @Transactional
    public TreinadorDTO atualizarTreinador(Long id, TreinadorDTO dto) {
        Treinador t = findTreinadorById(id);
        t.setNome(dto.getNome());
        t.setIdade(dto.getIdade());
        t.setCidade(dto.getCidade());
        Treinador tSalvo = treinadorRepo.save(t);

        return treinadorMapper.toDTO(tSalvo);
    }

    @Transactional
    public void deletarTreinador(Long id) {
        if (!treinadorRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinador com ID " + id + " não encontrado");
        }
        treinadorRepo.deleteById(id);
    }
    private Treinador findTreinadorById(Long id) {
        return treinadorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treinador com ID " + id + " não encontrado"));
    }
}