package com.implemntacaoSw.Campeonatos.service;

import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoResumoDTO;
import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorResumoDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeResponseDTO;
import com.implemntacaoSw.Campeonatos.entity.Campeonato;
import com.implemntacaoSw.Campeonatos.entity.Time;
import com.implemntacaoSw.Campeonatos.exception.BusinessException;
import com.implemntacaoSw.Campeonatos.exception.ResourceNotFoundException;
import com.implemntacaoSw.Campeonatos.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    /**
     * Converte a entidade Time para o DTO de resposta, incluindo jogadores (1:N)
     * e campeonatos dos quais participa (N:N), ambos em versão resumida.
     * (antes ficava em TimeMapper; agora mora aqui no Service.)
     */
    private TimeResponseDTO toResponseDTO(Time time) {
        List<JogadorResumoDTO> jogadores = time.getJogadores().stream()
                .map(j -> new JogadorResumoDTO(j.getId(), j.getNome(), j.getPosicao(), j.getNumeroCamisa()))
                .sorted(Comparator.comparing(JogadorResumoDTO::nome))
                .toList();

        List<CampeonatoResumoDTO> campeonatos = time.getCampeonatos().stream()
                .map(c -> new CampeonatoResumoDTO(c.getId(), c.getNome(), c.getTemporada()))
                .sorted(Comparator.comparing(CampeonatoResumoDTO::nome))
                .toList();

        return new TimeResponseDTO(
                time.getId(),
                time.getNome(),
                time.getCidade(),
                time.getEstado(),
                time.getAnoFundacao(),
                jogadores,
                campeonatos
        );
    }

    @Transactional(readOnly = true)
    public List<TimeResponseDTO> listarTodos(){
        return timeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    public TimeResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    /**
     * Retorna os jogadores de um time em formato resumido - o time já é
     * conhecido pela URL (/times/{id}/jogadores), então não repetimos
     * essa informação em cada jogador.
     */
    @Transactional(readOnly = true)
    public List<JogadorResumoDTO> listarJogadoresDoTime(Long id) {
        Time time = buscarEntidadePorId(id);
        return time.getJogadores().stream()
                .map(j -> new JogadorResumoDTO(j.getId(), j.getNome(), j.getPosicao(), j.getNumeroCamisa()))
                .sorted(Comparator.comparing(JogadorResumoDTO::nome))
                .toList();
    }

    public TimeResponseDTO criar(TimeRequestDTO dto) {
        if (timeRepository.existsByNomeIgnoreCase(dto.nome().trim())) {
            throw new BusinessException("Já existe um time com o nome '" + dto.nome().trim() + "'");
        }
        Time time = new Time();
        aplicarDados(time, dto);
        return toResponseDTO(timeRepository.save(time));
    }

    public TimeResponseDTO atualizar(Long id, TimeRequestDTO dto) {
        Time time = buscarEntidadePorId(id);
        if (timeRepository.existsByNomeIgnoreCaseAndIdNot(dto.nome().trim(), id)) {
            throw new BusinessException("Já existe outro time com o nome '" + dto.nome().trim() + "'");
        }
        aplicarDados(time, dto);
        return toResponseDTO(timeRepository.save(time));
    }

    /**
     * Remove o time (e seus jogadores). Se ele participar de campeonatos, os
     * vínculos são desfeitos antes: o lado "dono" da relação N:N é o Campeonato,
     * então sem isso o banco recusaria a exclusão (chave estrangeira).
     */
    public void deletar(Long id) {
        Time time = buscarEntidadePorId(id);

        for (Campeonato campeonato : new ArrayList<>(time.getCampeonatos())) {
            campeonato.getTimes().remove(time);
        }
        time.getCampeonatos().clear();

        timeRepository.delete(time);
    }

    private Time buscarEntidadePorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time não encontrado com id " + id));
    }

    private void aplicarDados(Time time, TimeRequestDTO dto) {
        time.setNome(dto.nome().trim());
        time.setCidade(dto.cidade());
        time.setEstado(dto.estado());
        time.setAnoFundacao(dto.anoFundacao());
    }


}
