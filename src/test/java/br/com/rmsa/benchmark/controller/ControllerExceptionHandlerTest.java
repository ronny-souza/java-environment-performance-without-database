package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    @DisplayName("Checking if EmptyFileException will return the exception message and status 400")
    void checkingIfEmptyFileExceptionWillReturnExceptionMessageAndStatus400() {
        /* Arrange */
        EmptyFileException emptyFileException = new EmptyFileException("This is a sample for the error");

        /* Act */
        ResponseEntity<ProblemDetail> response = this.controllerExceptionHandler.handlerEmptyFileException(emptyFileException);

        /* Assert */
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getDetail());
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals(emptyFileException.getMessage(), response.getBody().getDetail());
        Assertions.assertEquals(400, response.getBody().getStatus());
    }

    @Test
    @DisplayName("Checking if FileProcessorException will return the exception message and status 500")
    void checkingIfFileProcessorExceptionWillReturnExceptionMessageAndStatus500() {
        /* Arrange */
        FileProcessorException fileProcessorException = new FileProcessorException("This is a sample for the error");

        /* Act */
        ResponseEntity<ProblemDetail> response =
                this.controllerExceptionHandler.handlerFileProccessorException(fileProcessorException);

        /* Assert */
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getDetail());
        Assertions.assertTrue(response.getStatusCode().is5xxServerError());
        Assertions.assertEquals(fileProcessorException.getMessage(), response.getBody().getDetail());
        Assertions.assertEquals(500, response.getBody().getStatus());
    }
}