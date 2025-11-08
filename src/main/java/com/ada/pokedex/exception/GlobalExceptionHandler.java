package com.ada.pokedex.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
/**
 * Captura exceções globalmente e as formata em uma resposta JSON padronizada.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * DTO para a Resposta de Erro Padrão
     */
    public record ApiErrorResponse(
            int status,
            String error,
            String message,
            LocalDateTime timestamp
    ) {}
    /**
     * Manipula erros de validação
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String mensagem = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiErrorResponse apiError = new ApiErrorResponse(
                status.value(),
                HttpStatus.valueOf(status.value()).getReasonPhrase(),
                mensagem,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, status);
    }
    /**
     * Manipula erros que nós mesmos lançamos
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException ex) {

        ApiErrorResponse apiError = new ApiErrorResponse(
                ex.getStatusCode().value(),
                ex.getStatusCode().toString(),
                ex.getReason(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }
    /**
     * Manipulador "pega-tudo" para erros inesperados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {

        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro inesperado no servidor.",
                LocalDateTime.now()
        );
        ex.printStackTrace();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}