package com.implemntacaoSw.Campeonatos.dto.jogador;

import com.implemntacaoSw.Campeonatos.dto.time.TimeResumoDTO;

import java.time.LocalDate;

public record JogadorResponseDTO(

        Long id,
        String nome,
        String posicao,
        Integer numeroCamisa,
        LocalDate dataNascimento,
        TimeResumoDTO time
) {
}
