package br.com.rmsa.benchmark.service;

import br.com.rmsa.benchmark.exception.EmptyFileException;
import br.com.rmsa.benchmark.exception.FileProcessorException;
import br.com.rmsa.benchmark.model.StopWatch;
import br.com.rmsa.benchmark.model.transport.MetadataDTO;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvReaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderService.class);

    public int read(MultipartFile file) throws EmptyFileException, FileProcessorException {
        StopWatch csvParseStopWatcher = StopWatch.start().withContext(String.format("Read CSV file with %d Bytes", file.getSize()));
        LOGGER.info("Starting CSV file conversion process...");
        if (file.isEmpty()) {
            throw new EmptyFileException("CSV cannot be parsed because is empty");
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            BeanListProcessor<MetadataDTO> beanListProcessor = new BeanListProcessor<>(MetadataDTO.class);
            CsvParser csvParser = this.newCsvParserInstance(beanListProcessor);
            csvParser.parse(reader);
            List<MetadataDTO> metadatas = beanListProcessor.getBeans();
            LOGGER.info("CSV parsing result in {} objects", metadatas.size());
            csvParseStopWatcher.closeAndReport();
            return metadatas.size();
        } catch (Exception e) {
            throw new FileProcessorException(e.getMessage());
        }
    }

    private CsvParser newCsvParserInstance(BeanListProcessor<MetadataDTO> beanListProcessor) {
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.setIgnoreLeadingWhitespaces(true);
        csvParserSettings.setSkipEmptyLines(true);
        csvParserSettings.setProcessor(beanListProcessor);
        csvParserSettings.setHeaderExtractionEnabled(true);
        return new CsvParser(csvParserSettings);
    }
}
