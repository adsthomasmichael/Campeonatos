package com.implemntacaoSw.Campeonatos.service;

import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorResponseDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeResumoDTO;
import com.implemntacaoSw.Campeonatos.entity.Jogador;
import com.implemntacaoSw.Campeonatos.entity.Time;
import com.implemntacaoSw.Campeonatos.exception.ResourceNotFoundException;
import com.implemntacaoSw.Campeonatos.repository.JogadorRepository;
import com.implemntacaoSw.Campeonatos.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final TimeRepository timeRepository;

    public JogadorService(JogadorRepository jogadorRepository, TimeRepository timeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<JogadorResponseDTO> listarTodos() {
        return jogadorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public JogadorResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public JogadorResponseDTO criar(JogadorRequestDTO dto) {
        Jogador jogador = new Jogador();
        aplicarDados(jogador, dto);
        return toResponseDTO(jogadorRepository.save(jogador));
    }

    public JogadorResponseDTO atualizar(Long id, JogadorRequestDTO dto) {
        Jogador jogador = buscarEntidadePorId(id);
        aplicarDados(jogador, dto);
        return toResponseDTO(jogadorRepository.save(jogador));
    }

    public void deletar(Long id) {
        Jogador jogador = buscarEntidadePorId(id);
        jogadorRepository.delete(jogador);
    }

    private Jogador buscarEntidadePorId(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com id " + id));
    }

    private void aplicarDados(Jogador jogador, JogadorRequestDTO dto) {
        Time time = timeRepository.findById(dto.timeId())
                .orElseThrow(() -> new ResourceNotFoundException("Time não encontrado com id " + dto.timeId()));

        jogador.setNome(dto.nome());
        jogador.setPosicao(dto.posicao());
        jogador.setNumeroCamisa(dto.numeroCamisa());
        jogador.setDataNascimento(dto.dataNascimento());
        jogador.setTime(time);
    }

    /**
     * Converte a entidade Jogador para o DTO de resposta, incluindo
     * os dados resumidos do time ao qual pertence (relação N:1).
     * (antes ficava em JogadorMapper; agora mora aqui no Service.)
     */
    private JogadorResponseDTO toResponseDTO(Jogador jogador) {
        TimeResumoDTO time = jogador.getTime() != null
                ? new TimeResumoDTO(jogador.getTime().getId(), jogador.getTime().getNome())
                : null;

        return new JogadorResponseDTO(
                jogador.getId(),
                jogador.getNome(),
                jogador.getPosicao(),
                jogador.getNumeroCamisa(),
                jogador.getDataNascimento(),
                time
        );
    }
}

