package br.com.fiap.sustentabilidade.advice;

import br.com.fiap.sustentabilidade.exception.AgendamentoNaoEncontradoException;
import br.com.fiap.sustentabilidade.exception.AgendamentoNaoPodeSerAlteradoException;
import br.com.fiap.sustentabilidade.exception.EmissaoCarbonoNaoEncontradaException;
import br.com.fiap.sustentabilidade.exception.EmpresaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erro de validação nos campos.",
                errors
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(EmpresaNaoEncontradaException.class)
    public ResponseEntity<ErrorMessage> handleEmpresaNaoEncontrada(
            EmpresaNaoEncontradaException ex
    ) {

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(EmissaoCarbonoNaoEncontradaException.class)
    public ResponseEntity<ErrorMessage> handleEmissaoNaoEncontrada(
            EmissaoCarbonoNaoEncontradaException ex
    ) {

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(AgendamentoNaoEncontradoException.class)
    public ResponseEntity<ErrorMessage> handleAgendamentoNaoEncontrado(
            AgendamentoNaoEncontradoException ex
    ) {

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleEnumError(
            HttpMessageNotReadableException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        if (ex.getMessage().contains("FonteEmissao")) {

            errors.put(
                    "fonteEmissao",
                    "Valor inválido. Valores aceitos: PECUARIA, INDUSTRIA, TRANSPORTE, QUEIMA_COMBUSTIVEL, GERACAO_ENERGIA, AGROPECUARIA"
            );
        }

        if (ex.getMessage().contains("StatusAgendamento")) {

            errors.put(
                    "statusAgendamento",
                    "Valor inválido. Valores aceitos: PENDENTE, CONCLUIDO, CANCELADO"
            );
        }

        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erro ao converter valores do JSON.",
                errors
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(AgendamentoNaoPodeSerAlteradoException.class)
    public ResponseEntity<ErrorMessage> handleAgendamentoNaoPodeSerAlterado(
            AgendamentoNaoPodeSerAlteradoException ex
    ) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }
}
