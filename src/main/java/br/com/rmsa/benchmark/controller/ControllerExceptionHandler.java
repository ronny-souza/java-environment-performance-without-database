package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ProblemDetail> handlerEmptyFileException(EmptyFileException exception) {
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        ));
    }

    @ExceptionHandler(FileProcessorException.class)
    public ResponseEntity<ProblemDetail> handlerFileProccessorException(FileProcessorException exception) {
        return ResponseEntity.internalServerError().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage()
        ));
    }
}
