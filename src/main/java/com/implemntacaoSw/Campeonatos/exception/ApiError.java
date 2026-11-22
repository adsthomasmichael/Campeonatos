package com.implemntacaoSw.Campeonatos.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formato padrão usado em todas as respostas de erro da API.
 */

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> detalhes
) {

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, null);
    }

    public static ApiError of(int status, String error, String message, String path, List<String> detalhes) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, detalhes);
    }
}
