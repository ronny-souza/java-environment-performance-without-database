package br.com.rmsa.benchmark.service;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.model.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipService.class);
    private final String zipOutputPath;

    public ZipService(@Value("${benchmark.file.output-directory}") String zipOutputPath) {
        this.zipOutputPath = zipOutputPath;
    }

    /**
     * Reads a file, compresses it, and saves it to the directory specified in the properties file.
     *
     * @param file The file to be compressed.
     * @throws EmptyFileException     If the file you uploaded is invalid or empty.
     * @throws FileProcessorException If the server was unable to process the file zip request.
     * @author Ronyeri Marinho
     */
    public void zip(MultipartFile file) throws EmptyFileException, FileProcessorException {
        StopWatch stopWatch = StopWatch.start().withContext(String.format("Zip file with %d Bytes", file.getSize()));
        LOGGER.info("Starting file compression...");
        if (file.isEmpty()) {
            throw new EmptyFileException("Compression cannot be performed as the file is empty");
        }

        String outputPath = String.format("%s/%s.zip", this.zipOutputPath, file.getOriginalFilename());

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(file.getOriginalFilename()));
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(file.getBytes());
            zipOutputStream.closeEntry();

            stopWatch.closeAndReport();
        } catch (Exception e) {
            throw new FileProcessorException(e.getMessage());
        }
    }
}
