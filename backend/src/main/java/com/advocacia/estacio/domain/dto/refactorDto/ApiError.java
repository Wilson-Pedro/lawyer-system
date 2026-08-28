package com.advocacia.estacio.domain.dto.refactorDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        Integer status,
        String message,
        List<FieldErrorDto> fields
) {
    // construtor auxiliar 1: Para erros globais (sem lista de campos)
    public ApiError(Integer status, String message) {
        this(LocalDateTime.now(), status, message, null);
    }

    // construtor auxiliar 2: Para erros de validação (com lista de campos)
    public ApiError(Integer status, String message, List<FieldErrorDto> fields) {
        this(LocalDateTime.now(), status, message, fields);
    }

    // Record aninhado para os campos
    public record FieldErrorDto(String field, String message) {}
}
