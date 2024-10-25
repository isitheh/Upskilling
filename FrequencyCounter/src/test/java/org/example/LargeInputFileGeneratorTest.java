package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LargeInputFileGeneratorTest {

    private static final Logger logger = Logger.getLogger(LargeInputFileGeneratorTest.class.getName());
    private Path inputFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary input file
        inputFile = Files.createTempFile("input", ".txt");

        // Write a line of numbers to the file
        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {
            writer.write("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the temporary input file
        Files.deleteIfExists(inputFile);
    }

    @Test
    public void testLargeInputFileGenerator() {
        // Mock the output file path
        String outputFilePath = inputFile.toString();

        // Call the method to test
        LargeInputFileGenerator.largeInputFileGenerator();

        // Verify that the file was created and contains data
        File file = new File(outputFilePath);
        assertTrue(file.exists(), "The file should exist.");
        assertTrue(file.length() > 0, "The file should not be empty.");

        // Optionally, you can read the file and verify its content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                assertTrue(line.matches("\\[(-?\\d+(,\\s*)?)+\\]"), "Each line should contain integers separated by commas and enclosed in brackets.");
                lineCount++;
            }
            assertTrue(lineCount > 0, "The file should contain multiple lines.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while reading the file", e);
        }
    }
}