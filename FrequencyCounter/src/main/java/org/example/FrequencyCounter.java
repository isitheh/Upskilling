package org.example;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.util.Map.Entry;
import java.util.logging.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrequencyCounter {
    private static final Logger logger = Logger.getLogger(FrequencyCounter.class.getName());

    public static void main(String[] args) {
        String outputFile = "output.txt";
        processFile(outputFile);
    }

    public static String chooseInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            logger.log(Level.INFO, "No file selected.");
        }
        return null;
    }

    public static void processFile(String outputFile) {
        String inputFile = chooseInputFile();
        assert inputFile != null;
        File input = new File(inputFile);
        if (input.exists()) {
            logger.log(Level.INFO, "Processing the large dataset from the file system.");
            try (BufferedReader reader = Files.newBufferedReader(input.toPath());
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("[\\[\\],]", "").replaceAll("\\s+", " ");
                    String[] numbers = line.split("\\s+");
                    Arrays.sort(numbers);
                    int mostFrequent = getMostFrequentEntry(numbers);
                    writer.write(mostFrequent + "\n");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error occurred while processing the file", e);
            }
        } else {
            logger.log(Level.WARNING, "Input file does not exist at the specified path: " + inputFile);
        }
    }

    static int getMostFrequentEntry(@NotNull String @NotNull [] numbers) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for(String number: numbers) {
            int num = Integer.parseInt(number);
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        return getMostFrequentEntry(frequencyMap);
    }

    static int getMostFrequentEntry(Map<Integer, Integer> frequencyMap) {
        // Sort the frequencyMap by keys in ascending order using TreeMap so that the smaller number appears first.
        Map<Integer, Integer> sortedMap = new TreeMap<>(frequencyMap);
        int mostFrequentEntry = 0;
        int frequency = 0;

        for (Entry<Integer, Integer> allEntries : sortedMap.entrySet()) {
            if(frequency < allEntries.getValue()) {
                mostFrequentEntry = allEntries.getKey();
                frequency = allEntries.getValue();
            }
        }
        return mostFrequentEntry;
    }
}