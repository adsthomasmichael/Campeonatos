package com.implemntacaoSw.Campeonatos.dto.campeonato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CampeonatoRequestDTO(

        @NotBlank (message = "O nome do campeonato é obrigatorio")
        String nome,

        @NotBlank(message = "O esporte é obrigatório")
        String esporte,

        @NotBlank(message = "A temporada é obrigatória (ex: 2026)")
        String temporada,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim


) {
}
