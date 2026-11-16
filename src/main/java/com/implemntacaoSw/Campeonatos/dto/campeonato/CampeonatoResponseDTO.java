package com.implemntacaoSw.Campeonatos.dto.campeonato;

import com.implemntacaoSw.Campeonatos.dto.time.TimeResumoDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO retornado pela API ao consultar um Campeonato.
 * Inclui a lista resumida dos times participantes (relação N:N).
 */

public record CampeonatoResponseDTO(

        Long id,
        String nome,
        String esporte,
        String temporada,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<TimeResumoDTO> times

) {
}
