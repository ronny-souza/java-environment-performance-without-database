package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.model.operations.GenerateCsvForm;
import br.com.rmsa.benchmark.service.CsvReaderService;
import br.com.rmsa.benchmark.service.CsvWriterService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CsvController.class)
class CsvControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvReaderService csvReaderService;

    @MockBean
    private CsvWriterService csvWriterService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        this.objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @DisplayName("Checking if size-based CSV generation returns 200")
    void checkingIfSizeBasedCSVGenerationReturns200() throws Exception {
        GenerateCsvForm form = new GenerateCsvForm(1);
        String formAsJson = this.objectMapper.writeValueAsString(form);

        Mockito.doNothing().when(this.csvWriterService).generateSizeBasedCsv(form);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/csv/generate")
                        .content(formAsJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking if the CSV reader returns 200 when process successfully")
    void checkingIfTheCsvReaderReturns200WhenProcessSuccessfully() throws Exception {

        String content = """
                firstname,lastname,age
                testfirst,testlast,18
                """;


        MockMultipartFile multipartFile = new MockMultipartFile(
                "csv",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doNothing().when(this.csvReaderService).read(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/csv/read")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking if CSV reader returns 400 when CSV file is empty")
    void checkingIfCsvReaderReturns400WhenFileIsEmpty() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        Mockito.doThrow(EmptyFileException.class).when(this.csvReaderService).read(file);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/csv/read")
                        .content(file.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Checking if the CSV reader returns 500 when unable to process the file")
    void checkingIfTheCsvReaderReturns500WhenUnableToProcessFile() throws Exception {

        String content = """
                firstname,lastname,age
                testfirst,testlast,18
                """;


        MockMultipartFile multipartFile = new MockMultipartFile(
                "csv",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doThrow(FileProcessorException.class).when(this.csvReaderService).read(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/csv/read")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError());
    }
}