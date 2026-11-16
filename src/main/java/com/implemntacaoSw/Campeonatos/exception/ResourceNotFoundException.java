package com.implemntacaoSw.Campeonatos.exception;

/**
 * Lançada quando um Campeonato, Time ou Jogador não é encontrado pelo id.
 * Tratada globalmente pelo GlobalExceptionHandler, retornando HTTP 404.
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
