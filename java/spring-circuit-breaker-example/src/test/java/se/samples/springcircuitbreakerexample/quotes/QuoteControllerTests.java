package se.samples.springcircuitbreakerexample.quotes;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteControllerTests {

    private static final String QUOTE_TEXT = "Test quote";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuoteService quoteService;

    @Test
    void getQuote_shouldReturn_200() throws Exception {
        final var mockQuote = createMockQuote();
        given(quoteService.getQuote(true)).willReturn(mockQuote);
        mockMvc.perform(get("/quote?useCorrectUri=true")).andExpect(status().isOk())
                .andExpect(content().string(QUOTE_TEXT));

    }

    private Quote createMockQuote() {
        final var quote = new Quote();
        quote.setType("success");
        final var valueType = new ValueType();
        valueType.setId(1L);
        valueType.setQuote(QUOTE_TEXT);
        quote.setValue(valueType);
        return quote;
    }
}