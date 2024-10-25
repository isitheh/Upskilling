package org.example;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
public class LargeDatasetGenerator {
    private static final Logger logger = Logger.getLogger(FrequencyCounter.class.getName());

    public static void largeInputFileGenerator() {
        String outputFile = "input.txt";
        int numberOfLines = 10; // Number of lines to generate
        int maxNumbersPerLine = 5; // Maximum numbers per line

        logger.log(Level.INFO, "Create the large dataset.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Random random = new Random();

            for (int i = 0; i < numberOfLines; i++) {
                StringBuilder line = new StringBuilder();
                int numbersInLine = random.nextInt(maxNumbersPerLine) + 1; // Random number of integers per line

                for (int j = 0; j < numbersInLine; j++) {
                    int randomInt = random.nextInt();
                    line.append(randomInt).append(" ");
                }
                writer.write(line.toString().trim());
                writer.newLine();
            }
            logger.log(Level.INFO, "Large dataset generated successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while generating the file or values", e);
        }
    }
}
