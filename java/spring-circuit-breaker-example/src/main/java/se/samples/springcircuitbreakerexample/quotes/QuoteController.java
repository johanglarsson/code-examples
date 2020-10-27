package se.samples.springcircuitbreakerexample.quotes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("/quote")
    String getQuote(@RequestParam(defaultValue = "true") Boolean useCorrectUri) {
        var quote = quoteService.getQuote(useCorrectUri).getValue().getQuote();
        log.info("Got quote {}", quote);
        return quote;
    }
}