package org.example;

import java.util.Scanner;
import java.util.logging.*;
import org.example.aigenerator.AzureOpenAiGenerator;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your prompt: ");
        // The prompt you want to send to Mistral
        String promptText = scanner.nextLine();
        // Call the Mistral AI API to generate text based on the prompt
        String generatedText = AzureOpenAiGenerator.aiResponseGenerator(promptText);
        logger.log(Level.INFO, "Generated text: " + generatedText);
    }
}