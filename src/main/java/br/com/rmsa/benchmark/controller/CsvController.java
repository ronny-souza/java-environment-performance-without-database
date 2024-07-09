package br.com.rmsa.benchmark.controller;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.model.StopWatch;
import br.com.rmsa.benchmark.model.operations.GenerateCsvForm;
import br.com.rmsa.benchmark.service.CsvReaderService;
import br.com.rmsa.benchmark.service.CsvWriterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
@Tag(name = "CSV Controller", description = "Controller for managing all CSV related operations.")
public class CsvController {
    private final CsvReaderService csvReaderService;
    private final CsvWriterService csvWriterService;

    public CsvController(CsvReaderService csvReaderService, CsvWriterService csvWriterService) {
        this.csvReaderService = csvReaderService;
        this.csvWriterService = csvWriterService;
    }

    @Operation(summary = "Generates a CSV file with random data based on the specified file size.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The CSV file has been created and made available locally in the specified directory."
            )
    })
    @PostMapping("/generate")
    public ResponseEntity<Void> generateSizeBasedCsv(@RequestBody @Valid GenerateCsvForm form) {
        StopWatch stopWatch = StopWatch.start()
                .withContext(String.format("Generate CSV file with %sMB", form.sizeInMB()));

        this.csvWriterService.generateSizeBasedCsv(form);
        stopWatch.closeAndReport();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reads a CSV file, converts it into Java objects and persists the information in the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The operation was carried out successfully."
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "The CSV file you uploaded is invalid or empty.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    }),

            @ApiResponse(
                    responseCode = "500",
                    description = "The server was unable to process the CSV read and persist request.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    })
    })
    @PostMapping("/read")
    public ResponseEntity<String> read(@RequestParam("csv") MultipartFile csvFile) throws EmptyFileException, FileProcessorException {
        int amountOfGeneratedEntities = this.csvReaderService.read(csvFile);
        return ResponseEntity.ok(String.format("CSV extraction generates %s items", amountOfGeneratedEntities));
    }
}
