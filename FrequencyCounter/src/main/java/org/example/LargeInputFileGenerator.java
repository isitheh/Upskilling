package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LargeInputFileGenerator {

    private static final Logger logger = Logger.getLogger(LargeInputFileGenerator.class.getName());

    public static void largeInputFileGenerator() {
        String outputFile = "input.txt";
        int numberOfLines = 10; // Number of lines to generate
        int maxNumbersPerLine = 5; // Maximum numbers per line, adjusted based on your example needs

        logger.log(Level.INFO, "Creating the large dataset.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Random random = new Random();

            for (int i = 0; i < numberOfLines; i++) {
                StringBuilder line = new StringBuilder();
                int numbersInLine = random.nextInt(maxNumbersPerLine - 3) + 4; // Generates at least 4 numbers per line

                line.append("["); // Start with the opening bracket
                for (int j = 0; j < numbersInLine; j++) {
                    int randomInt = random.nextInt(10) + 1; // Generates random numbers between 1 and 10 for simplicity
                    line.append(randomInt);
                    if (j < numbersInLine - 1) {
                        line.append(", "); // Append comma and space between numbers
                    }
                }
                line.append("]"); // Close with the closing bracket
                writer.write(line.toString());
                writer.newLine();
            }
            logger.log(Level.INFO, "Large dataset generated successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while generating the file or values", e);
        }
    }
}