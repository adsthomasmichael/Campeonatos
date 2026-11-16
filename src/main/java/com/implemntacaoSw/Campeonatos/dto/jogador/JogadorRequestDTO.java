package com.implemntacaoSw.Campeonatos.dto.jogador;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;




import java.time.LocalDate;

public record JogadorRequestDTO(

        @NotBlank(message = "O nome do jogador é obrigatório")
        String nome,

        String posicao,

        @Positive(message = "O número da camisa deve ser positivo")
        Integer numeroCamisa,

        LocalDate dataNascimento,

        @NotNull(message = "O campo timeID é obrigatório (id do time do jogador)")
        Long timeId

) {
}
