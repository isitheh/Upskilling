package org.example.aigenerator;

import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.logging.*;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;

public class AzureOpenAiGenerator {
    private static final String LM_STUDIO_URL = "http://localhost:1234/v1/chat/completions";
    private static final Logger logger = Logger.getLogger(AzureOpenAiGenerator.class.getName());
    private static HttpURLConnection mockConnection;  // Add a static mock connection for testing

    public static String aiResponseGenerator(String promptText) {
        String generatedText = null;
        try {
            HttpURLConnection connection = getHttpURLConnection(promptText);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                logger.log(Level.INFO, "Request successful! Mistral AI has responded.");
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = in.lines().collect(Collectors.joining("\n"));
                    JSONObject jsonResponse = new JSONObject(response);
                    generatedText = processJSONResponseObject(jsonResponse);
                }
            } else {
                logger.log(Level.SEVERE, "Request failed. HTTP error code: " + responseCode);
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String errorResponse = in.lines().collect(Collectors.joining("\n"));
                    logger.log(Level.SEVERE, "Error response: " + errorResponse);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failure processing prompt.");
        }
        return generatedText != null ? generatedText : "Error: No response generated.";
    }

    @NotNull
    private static HttpURLConnection getHttpURLConnection(String promptText) throws IOException {
        /*
         * If we set max_tokens to 100, for example, if the prompt sent to the
         * model uses 50 tokens, the response can only be up to 50 tokens long.
         * Temperature of close to 0.9/1.0 indicates that the model is more quirky
         */
        String jsonRequest = "{"
                + "\"model\": \"mistral\","
                + "\"messages\": ["
                + "   {\"role\": \"system\", \"content\": \"You are a helpful assistant. You will talk like a pirate.\"},"
                + "   {\"role\": \"user\", \"content\": \"" + promptText + "\"}"
                + "],"
                + "\"max_tokens\": 1000,"
                + "\"temperature\": 0.7"
                + "}";

        return getUrlConnection(jsonRequest);
    }

    public static void setMockConnection(HttpURLConnection connection) {
        mockConnection = connection;
    }

    @NotNull
    private static HttpURLConnection getUrlConnection(String jsonRequest) throws IOException {
        URL url = new URL(LM_STUDIO_URL);

        if (mockConnection != null) {
            return mockConnection;  // Use the mock connection if available
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        return connection;
    }

    private static String processJSONResponseObject(JSONObject jsonResponse) {
        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);

        if (firstChoice.has("message")) {
            Object messageObj = firstChoice.get("message");
            if (messageObj instanceof JSONObject) {
                JSONObject message = firstChoice.getJSONObject("message");
                return message.getString("content");
            } else if (messageObj instanceof String) {
                return firstChoice.getString("message");
            } else {
                logger.log(Level.INFO, "Unexpected data type for 'message'");
            }
        } else {
            logger.log(Level.WARNING, "'message' field not found");
        }
        return null;
    }
}
