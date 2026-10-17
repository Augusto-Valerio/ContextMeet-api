package br.com.fiap.contextmeet.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptions {

    // Erros de validação
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarArgumentoInvalido(IllegalArgumentException erro) {
        Map<String, String> resposta = Map.of("erro", erro.getMessage());

        return ResponseEntity.badRequest().body(resposta);
    }

    // Registro não encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> tratarRegistroNaoEncontrado(NoSuchElementException erro) {
        Map<String, String> resposta = Map.of("erro", erro.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    // Erros de banco de dados ou driver
    @ExceptionHandler({SQLException.class, ClassNotFoundException.class})
    public ResponseEntity<Map<String, String>> tratarErroDeBanco(Exception erro) {
        Map<String, String> resposta = Map.of("erro", "Não foi possível acessar o banco de dados.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    // Outros erros não previstos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErroInesperado(Exception erro) {
        Map<String, String> resposta = Map.of("erro", "Ocorreu um erro interno inesperado.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

}
