package com.implemntacaoSw.Campeonatos.service;

import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoResponseDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeResumoDTO;
import com.implemntacaoSw.Campeonatos.entity.Campeonato;
import com.implemntacaoSw.Campeonatos.entity.Time;
import com.implemntacaoSw.Campeonatos.exception.BusinessException;
import com.implemntacaoSw.Campeonatos.exception.ResourceNotFoundException;
import com.implemntacaoSw.Campeonatos.repository.CampeonatoRepository;
import com.implemntacaoSw.Campeonatos.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final TimeRepository timeRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository, TimeRepository timeRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<CampeonatoResponseDTO> listarTodos() {
        return campeonatoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CampeonatoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public CampeonatoResponseDTO criar(CampeonatoRequestDTO dto) {
        validarDatas(dto);
        Campeonato campeonato = new Campeonato();
        aplicarDados(campeonato, dto);
        return toResponseDTO(campeonatoRepository.save(campeonato));
    }

    public CampeonatoResponseDTO atualizar(Long id, CampeonatoRequestDTO dto) {
        Campeonato campeonato = buscarEntidadePorId(id);
        validarDatas(dto);
        aplicarDados(campeonato, dto);
        return toResponseDTO(campeonatoRepository.save(campeonato));
    }

    public void deletar(Long id) {
        Campeonato campeonato = buscarEntidadePorId(id);
        campeonatoRepository.delete(campeonato);
    }

    /**
     * Associa um Time a um Campeonato (gerência da relação N:N).
     */
    public CampeonatoResponseDTO adicionarTime(Long campeonatoId, Long timeId) {
        Campeonato campeonato = buscarEntidadePorId(campeonatoId);
        Time time = buscarTimePorId(timeId);

        if (campeonato.getTimes().contains(time)) {
            throw new BusinessException("O time '" + time.getNome() + "' já participa deste campeonato");
        }

        campeonato.getTimes().add(time);
        time.getCampeonatos().add(campeonato);

        return toResponseDTO(campeonatoRepository.save(campeonato));
    }

    /**
     * Remove a associação entre um Time e um Campeonato.
     */
    public CampeonatoResponseDTO removerTime(Long campeonatoId, Long timeId) {
        Campeonato campeonato = buscarEntidadePorId(campeonatoId);
        Time time = buscarTimePorId(timeId);

        campeonato.getTimes().remove(time);
        time.getCampeonatos().remove(campeonato);

        return toResponseDTO(campeonatoRepository.save(campeonato));
    }

    private Campeonato buscarEntidadePorId(Long id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campeonato não encontrado com id " + id));
    }

    private Time buscarTimePorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time não encontrado com id " + id));
    }

    /* A data de término, quando informada, não pode ser anterior à data de início. */
    private void validarDatas(CampeonatoRequestDTO dto) {
        if (dto.dataFim() != null && dto.dataFim().isBefore(dto.dataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
    }

    private void aplicarDados(Campeonato campeonato, CampeonatoRequestDTO dto) {
        campeonato.setNome(dto.nome());
        campeonato.setEsporte(dto.esporte());
        campeonato.setTemporada(dto.temporada());
        campeonato.setDataInicio(dto.dataInicio());
        campeonato.setDataFim(dto.dataFim());
    }

    /**
     * Converte a entidade Campeonato para o DTO de resposta da API,
     * incluindo a lista resumida dos times participantes (relação N:N).*/
    private CampeonatoResponseDTO toResponseDTO(Campeonato campeonato) {
        List<TimeResumoDTO> times = campeonato.getTimes().stream()
                .map(t -> new TimeResumoDTO(t.getId(), t.getNome()))
                .sorted(Comparator.comparing(TimeResumoDTO::nome))
                .toList();

        return new CampeonatoResponseDTO(
                campeonato.getId(),
                campeonato.getNome(),
                campeonato.getEsporte(),
                campeonato.getTemporada(),
                campeonato.getDataInicio(),
                campeonato.getDataFim(),
                times
        );
    }
}
