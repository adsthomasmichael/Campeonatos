package com.implemntacaoSw.Campeonatos.exception;

/**
 * Lançada quando uma regra de negócio é violada
 * (ex: tentar associar um time que já participa do campeonato).
 * Tratada globalmente pelo GlobalExceptionHandler, retornando HTTP 409.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
