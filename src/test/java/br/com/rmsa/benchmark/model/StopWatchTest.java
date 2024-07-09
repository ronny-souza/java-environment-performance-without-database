package br.com.rmsa.benchmark.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StopWatchTest {

    @Test
    @DisplayName("Checking if static start method returns a StopWatch instance")
    void checkingIfStaticStartMethodReturnsAStopWatchInstance() {
        StopWatch stopWatch = StopWatch.start();
        Assertions.assertNotNull(stopWatch);
    }
}