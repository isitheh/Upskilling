package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FrequencyCounterTest {

    private Path outputFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary output file
        outputFile = Files.createTempFile("output", ".txt");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(outputFile);
    }

    @Test
    public void testGetMostFrequentEntryFromArray() {
        // Mock input data
        String[] numbers = {"1", "2", "2", "3", "3", "3", "4", "4", "4", "4"};

        // Expected output
        int expectedMostFrequent = 4;

        // Call the method to test
        int actualMostFrequent = FrequencyCounter.getMostFrequentEntry(numbers);

        // Assert the result
        assertEquals(expectedMostFrequent, actualMostFrequent);
    }

    @Test
    public void testGetMostFrequentEntryFromMap() {
        // Mock input data
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put(1, 1);
        frequencyMap.put(2, 2);
        frequencyMap.put(3, 3);
        frequencyMap.put(4, 4);

        // Expected output
        int expectedMostFrequent = 4;

        // Call the method to test
        int actualMostFrequent = FrequencyCounter.getMostFrequentEntry(frequencyMap);

        // Assert the result
        assertEquals(expectedMostFrequent, actualMostFrequent);
    }

    @Test
    public void testProcessFile() throws IOException {
        // Mock the chooseInputFile method to return the path of the temporary input file
        FrequencyCounter frequencyCounterSpy = spy(FrequencyCounter.class);
        FrequencyCounter.chooseInputFile();

        // Call the method to test
        FrequencyCounter.processFile(outputFile.toString());

        // Read the output file and its content
        try (BufferedReader reader = Files.newBufferedReader(outputFile)) {
            String line = reader.readLine();
            assertEquals("4", line.trim());

            line = reader.readLine();
            assertEquals("7", line.trim());
        }
    }
}