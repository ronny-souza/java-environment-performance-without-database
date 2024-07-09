package br.com.rmsa.benchmark.service;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.model.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UnzipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnzipService.class);
    private final String zipOutputPath;

    public UnzipService(@Value("${benchmark.file.output-directory}") String zipOutputPath) {
        this.zipOutputPath = zipOutputPath;
    }

    /**
     * Reads a file, decompresses it, and saves it to the directory specified in the properties file.
     *
     * @param file The file to be decompressed.
     * @throws EmptyFileException     If the file you uploaded is invalid or empty.
     * @throws FileProcessorException If the server was unable to process the file zip request.
     * @author Ronyeri Marinho
     */
    public void unzip(MultipartFile file) throws EmptyFileException, FileProcessorException {
        StopWatch stopWatch = StopWatch.start().withContext(String.format("Read CSV file with %d Bytes", file.getSize()));
        LOGGER.info("Starting file decompression...");
        if (file.isEmpty()) {
            throw new EmptyFileException("Decompression cannot be performed as the file is empty");
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String filename = zipEntry.getName();
                File newFile = new File(String.format("%s%s%s", this.zipOutputPath, File.separator, filename));

                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();

                    try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, length);
                        }
                    }
                }

                zipEntry = zipInputStream.getNextEntry();
            }

        } catch (IOException e) {
            throw new FileProcessorException(e.getMessage());
        }

        stopWatch.closeAndReport();
    }
}
