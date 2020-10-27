package se.samples.springcircuitbreakerexample.quotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class QuoteConfig {

    @Bean("quoteWebClient")
    WebClient webClient() {
        return WebClient.create();
    }

}