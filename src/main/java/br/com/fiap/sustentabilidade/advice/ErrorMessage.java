package br.com.fiap.sustentabilidade.advice;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorMessage(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        Map<String, String> fields
) {
}