package org.example.aigenerator;

import org.junit.Test;
import org.junit.Before;
import java.io.InputStream;
import java.net.HttpURLConnection;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class AzureOpenAiGeneratorTest {

    private HttpURLConnection mockConnection;

    @Before
    public void setUp() throws Exception {
        // Mock HttpURLConnection
        mockConnection = mock(HttpURLConnection.class);

        // Mock the URL connection setup in the AzureOpenAiGenerator
        AzureOpenAiGenerator.setMockConnection(mockConnection); // You need to adjust your class to inject a mock
    }

    @Test
    public void testAiResponseGenerator_Success() throws Exception {
        // Mock a successful response code
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        // Mock the InputStream from the connection
        String mockResponse = "{\"choices\": [{\"message\": {\"content\": \"Ahoy! I be here to help ye.\"}}]}";
        InputStream mockInputStream = new ByteArrayInputStream(mockResponse.getBytes(StandardCharsets.UTF_8));

        when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        // Call the method under test
        String promptText = "Help me!";
        String result = AzureOpenAiGenerator.aiResponseGenerator(promptText);

        // Assert the response is as expected
        assertNotNull(result);
        assertEquals("Ahoy! I be here to help ye.", result);
    }

    @Test
    public void testAiResponseGenerator_Failure() throws Exception {
        // Mock a failure response code
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

        // Call the method under test
        String promptText = "Help me!";
        String result = AzureOpenAiGenerator.aiResponseGenerator(promptText);

        // Assert that the fallback message is returned
        assertEquals("Error: No response generated.", result);
    }
}
