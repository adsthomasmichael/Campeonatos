package com.implemntacaoSw.Campeonatos.dto.time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TimeRequestDTO(

        @NotBlank(message = "O nome do time é obrigatório")
        String nome,

        String cidade,

        @Size(max = 2, message = "Use a sigla do estado, ex: RS")
        String estado,

        Integer anoFundacao
) {
}
