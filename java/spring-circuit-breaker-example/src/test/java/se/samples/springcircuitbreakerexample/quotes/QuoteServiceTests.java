package se.samples.springcircuitbreakerexample.quotes;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@Slf4j
public class QuoteServiceTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static MockWebServer mockBackEnd;

    private QuoteService quoteService;

    @BeforeAll
    static void setup() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void teardown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        // Initialize mock backend server to respond to localhost
        log.info("Mocking localhost on port {}", mockBackEnd.getPort());
        var baseUri = URI.create(String.format("http://localhost:%s", mockBackEnd.getPort()));
        quoteService = new QuoteService(baseUri, baseUri, 30, WebClient.create());
    }

    @Test
    void getQuote_shouldReturnQuote_whenHttpOkFromBackend() throws JsonProcessingException {
        final var mockQuote = createTestQuote();
        // Enqueue mock backend response.
        mockBackEnd.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(mockQuote))
                .addHeader("Content-Type", "application/json"));
        assertThat(quoteService.getQuote(true)).isEqualTo(mockQuote);
    }

    @Test
    void getFallbackQuote_shouldReturnAFallbackQuote_whenCalled() {
        assertThat(quoteService.getFallbackQuote(true, new Exception())).isNotNull();
    }

    private Quote createTestQuote() {
        final var quote = new Quote();
        quote.setType("success");
        final var valueType = new ValueType();
        valueType.setId(1L);
        valueType.setQuote("Test quote");
        quote.setValue(valueType);
        return quote;
    }
}