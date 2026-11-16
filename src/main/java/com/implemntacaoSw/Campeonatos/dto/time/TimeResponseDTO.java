package com.implemntacaoSw.Campeonatos.dto.time;

import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoResumoDTO;
import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorResumoDTO;

import java.util.List;

public record TimeResponseDTO(

        Long id,
        String nome,
        String cidade,
        String estado,
        Integer anoFundacao,
        List<JogadorResumoDTO> jogadores,
        List<CampeonatoResumoDTO> campeonatos
) {
}
