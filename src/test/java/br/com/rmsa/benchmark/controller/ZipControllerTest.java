package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.service.UnzipService;
import br.com.rmsa.benchmark.service.ZipService;
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

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ZipController.class)
class ZipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZipService zipService;

    @MockBean
    private UnzipService unzipService;

    @Test
    @DisplayName("Checking if compress process returns 200 when operating successfully")
    void checkingIfZipReturns200WhenOperatingSuccessfully() throws Exception {
        String content = """
                firstname,lastname,age
                testfirst,testlast,18
                """;

        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToZip",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doNothing().when(this.zipService).zip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/zip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking if compress process returns 400 when file is empty")
    void checkingIfZipReturns400WhenFileIsEmpty() throws Exception {
        String content = "";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToZip",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doThrow(EmptyFileException.class).when(this.zipService).zip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/zip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Checking if compress process returns 500 when unable to process the file")
    void checkingIfZipReturns500WhenUnableToProcessFile() throws Exception {
        String content = "";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToZip",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doThrow(FileProcessorException.class).when(this.zipService).zip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/zip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Checking if decompress process returns 200 when operating successfully")
    void checkingIfUnzipReturns200WhenOperatingSuccessfully() throws Exception {
        String content = """
                firstname,lastname,age
                testfirst,testlast,18
                """;

        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToUnzip",
                "test.csv",
                "application/zip",
                content.getBytes()
        );

        Mockito.doNothing().when(this.unzipService).unzip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/unzip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking if decompress process returns 400 when file is empty")
    void checkingIfUnzipReturns400WhenFileIsEmpty() throws Exception {
        String content = "";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToUnzip",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doThrow(EmptyFileException.class).when(this.unzipService).unzip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/unzip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Checking if decompress process returns 500 when unable to process the file")
    void checkingIfUnzipReturns500WhenUnableToProcessFile() throws Exception {
        String content = "";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileToUnzip",
                "test.csv",
                "text/csv",
                content.getBytes()
        );

        Mockito.doThrow(FileProcessorException.class).when(this.unzipService).unzip(multipartFile);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/unzip")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError());
    }
}