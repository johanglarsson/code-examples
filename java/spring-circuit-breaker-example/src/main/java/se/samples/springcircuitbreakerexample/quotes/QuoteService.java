package se.samples.springcircuitbreakerexample.quotes;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
class QuoteService {

    @Value("${quote-service.uri}")
    private URI quoteServiceUri;

    @Value("${quote-service.bad-uri}")
    private URI quoteServiceBadUri;

    @Value("${quote-service.timeout-in-seconds:30}")
    private int quoteServiceTimeoutInSeconds;

    @Autowired
    @Qualifier("quoteWebClient")
    private WebClient quoteWebClient;

    // Needed because otherwise it sometimes threw TimeoutException upon first call.
    // I think its because the fallback is too slow.
    @HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
    @HystrixCommand(fallbackMethod = "getFallbackQuote")
    Quote getQuote(final boolean useCorrectUri) {
        final var uri = useCorrectUri ? quoteServiceUri : quoteServiceBadUri;
        return quoteWebClient.get().uri(uri).retrieve().bodyToMono(Quote.class)
                .block(Duration.ofSeconds(quoteServiceTimeoutInSeconds));

    }

    Quote getFallbackQuote(final boolean useCorrectUri, final Throwable t) {
        log.warn("Falling back to a fixed quote due to {}", t);
        final var quote = new Quote();
        final var valueType = new ValueType();
        valueType.setId(1L);
        valueType.setQuote("This is my fallback quote");
        quote.setValue(valueType);
        quote.setType("Test");
        return quote;
    }

    @PostConstruct
    void validate() {
        Objects.requireNonNull(quoteWebClient, "Webclient has not been initialized");
    }
}