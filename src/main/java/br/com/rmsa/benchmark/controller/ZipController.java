package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.service.UnzipService;
import br.com.rmsa.benchmark.service.ZipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "ZIP Controller", description = "Controller for managing all zip/unzip related operations.")
public class ZipController {

    private final ZipService zipService;
    private final UnzipService unzipService;

    public ZipController(ZipService zipService, UnzipService unzipService) {
        this.zipService = zipService;
        this.unzipService = unzipService;
    }

    @Operation(summary = "Reads a file, compresses it, and saves it to the directory specified in the properties file.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The operation was carried out successfully."
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "The file you uploaded is invalid or empty.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    }),

            @ApiResponse(
                    responseCode = "500",
                    description = "The server was unable to process the file zip request.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    })
    })
    @PostMapping("/zip")
    public ResponseEntity<Void> zipFile(@RequestParam("fileToZip") MultipartFile fileToZip) throws EmptyFileException, FileProcessorException {
        this.zipService.zip(fileToZip);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reads a file, decompresses it, and saves it to the directory specified in the properties file.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The operation was carried out successfully."
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "The file you uploaded is invalid or empty.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    }),

            @ApiResponse(
                    responseCode = "500",
                    description = "The server was unable to process the file unzip request.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    })
    })
    @PostMapping("/unzip")
    public ResponseEntity<Void> unzipFile(@RequestParam("fileToUnzip") MultipartFile fileToUnzip) throws EmptyFileException, FileProcessorException {
        this.unzipService.unzip(fileToUnzip);
        return ResponseEntity.ok().build();
    }
}
