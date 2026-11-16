package com.implemntacaoSw.Campeonatos.dto.campeonato;

/**
 * Versão resumida do Campeonato, usada dentro do TimeResponseDTO
 * para evitar referência circular no JSON.
 */
public record CampeonatoResumoDTO(

        Long id,
        String nome,
        String temporada
) {
}
