package br.com.rmsa.benchmark.service;

import br.com.rmsa.benchmark.model.operations.GenerateCsvForm;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CsvWriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvWriterService.class);

    private final Random random;
    private final String csvWriterOutputDirectory;

    public CsvWriterService(@Value("${benchmark.file.output-directory}") String csvWriterOutputDirectory) {
        this.random = new Random();
        this.csvWriterOutputDirectory = csvWriterOutputDirectory;
    }

    /**
     * Generates the CSV with a size in MB equivalent to that specified in the parameter.
     *
     * @param form Object containing the information necessary to assemble the CSV.
     * @author Ronyeri Marinho
     */
    public void generateSizeBasedCsv(GenerateCsvForm form) {
        LOGGER.info("Starting CSV generation...");
        String name = String.format("%s/BenchmarkFile-%sMB-%s.csv", this.csvWriterOutputDirectory, form.sizeInMB(), UUID.randomUUID());

        File newFile = new File(name);
        CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
        CsvWriter csvWriter = new CsvWriter(newFile, csvWriterSettings);

        long fileSizeInBytes = form.sizeInMB() * 1024L * 1024L;
        LOGGER.info("Desired file size: {} Bytes | {}MB", fileSizeInBytes, form.sizeInMB());

        csvWriter.writeHeaders(this.getHeaders());
        long currentFileSize = 0L;

        while (currentFileSize < fileSizeInBytes) {
            String firstname = this.generateString(15);
            String lastname = this.generateString(15);
            String email = String.format("%s@example.com", firstname);
            int age = random.nextInt(63) + 18;
            String gender = random.nextBoolean() ? "Male" : "Female";
            String phone = this.generateString(11);
            String address = this.generateString(12);
            String city = this.generateString(10);
            String state = this.generateString(12);
            String zipCode = this.generateString(8);
            String country = this.generateString(15);
            String job = this.generateString(20);
            csvWriter.writeRow(
                    firstname,
                    lastname,
                    email,
                    age,
                    gender,
                    phone,
                    address,
                    city,
                    state,
                    zipCode,
                    country,
                    job
            );
            currentFileSize = newFile.length();
        }
        LOGGER.info("Resulting file size: {} Bytes | {}MB", currentFileSize, currentFileSize / (1024 * 1024));
        csvWriter.close();
    }

    /**
     * Generates a random String with size based on the parameter.
     *
     * @param length The size of the String.
     * @return The random String.
     * @author Ronyeri Marinho
     */
    private String generateString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder nameAsStringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = this.random.nextInt(characters.length());
            nameAsStringBuilder.append(characters.charAt(index));
        }
        return nameAsStringBuilder.toString();
    }

    /**
     * Lists what the CSV file headers will be.
     *
     * @return The list of CSV headers.
     * @author Ronyeri Marinho
     */
    private List<String> getHeaders() {
        return List.of(
                "firstname",
                "lastname",
                "email",
                "age",
                "gender",
                "phone",
                "address",
                "city",
                "state",
                "zipcode",
                "country",
                "job"
        );
    }
}
