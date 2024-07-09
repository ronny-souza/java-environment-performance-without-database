package br.com.rmsa.benchmark.service;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CsvReaderServiceTest {

    @InjectMocks
    private CsvReaderService csvReaderService;

    @Test
    @DisplayName("Checking if empty file throws exception")
    void checkingIfEmptyFileThrowsException() {
        /* Arrange */
        String content = "";

        MockMultipartFile multipartFile = new MockMultipartFile(
                "csv",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        /* Act & Assert */
        Assertions.assertThrows(
                EmptyFileException.class, () -> this.csvReaderService.read(multipartFile)
        );
    }

    @Test
    @DisplayName("Checking if CSV parser works correctly")
    void checkingIfCsvParserWorksCorrectly() throws FileProcessorException, EmptyFileException {
        /* Arrange */
        String content = """
                firstname,lastname,email,age,gender,phone,address,city,state,zipcode,country,job
                MxAi1JXwJh45ynb,E2QFXadbtcHeozJ,MxAi1JXwJh45ynb@example.com,56,Male,7kbEfFUn0AW,DmATv2aQgeyu,GWAY5xCuWG,Pyp0fyKUyJah,s2Aw01Mi,j6VrpfKfHfyxEug,CY1H5gPzhrGTioM0DL58
                """;

        MockMultipartFile multipartFile = new MockMultipartFile(
                "csv",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        /* Act */
        int size = this.csvReaderService.read(multipartFile);

        /* Assert */
        Assertions.assertEquals(1, size);
    }
}