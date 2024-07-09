package br.com.rmsa.benchmark.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

public class StopWatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopWatch.class);
    private String context;
    private final LocalDateTime executionStartDateTime;

    private StopWatch() {
        this.executionStartDateTime = LocalDateTime.now();
    }

    public static StopWatch start() {
        return new StopWatch();
    }

    public StopWatch withContext(String context) {
        this.context = context;
        return this;
    }

    public synchronized void closeAndReport() {
        LocalDateTime executionFinishDateTime = LocalDateTime.now();
        LOGGER.info("============================================================================");
        LOGGER.info("Operation: {}", this.context != null ? this.context : "Operation not reported");
        LOGGER.info("Execution started at: {}", this.executionStartDateTime);
        LOGGER.info("Execution finished at: {}", executionFinishDateTime);
        Duration executionTime = Duration.between(this.executionStartDateTime, executionFinishDateTime);
        LOGGER.info("Total execution time: {}s | {}ms | {}n", executionTime.toSeconds(), executionTime.toMillis(), executionTime.toNanos());
        LOGGER.info("============================================================================");
    }
}
