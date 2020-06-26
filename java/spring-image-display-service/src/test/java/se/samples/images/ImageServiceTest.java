package se.samples.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.ImageLocation;
import se.samples.images.entities.ImageLocations;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(properties = {"app.useExternalRepository = false"})
class ImageServiceTest {

    private static final ImageLocation IMAGE_LOCATION = new ImageLocation("http://localhost:9000/myimage", "Test caption");

    @Autowired
    private ImageService imageService;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private ImageRepository mockImageRepository;
    
    private MockRestServiceServer mockServer;

    @BeforeEach
    void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Nested
    class givenValidLocations {

        @Test
        void shouldCreateImage_whenManyLocationsFromRepository() {

            var imageLocations = new ImageLocations(List.of(IMAGE_LOCATION, IMAGE_LOCATION));

            setupMockServer(ExpectedCount.twice(), MediaType.IMAGE_JPEG, OK);
            when(mockImageRepository.getImageLocations()).thenReturn(imageLocations);

            assertThat(imageService.getImages()).hasSize(2);
            mockServer.verify();

        }

    }

    @Nested
    class givenInvalidLocations {

        @Test
        void shouldFilterOutImage_whenNotAnImage() {

            setupMockServer(ExpectedCount.once(), MediaType.TEXT_PLAIN, OK);

            var imageLocations = new ImageLocations(List.of(IMAGE_LOCATION));

            when(mockImageRepository.getImageLocations()).thenReturn(imageLocations);

            assertThat(imageService.getImages()).isEmpty();
            mockServer.verify();

        }


        @Test
        void shouldFilterOutImage_whenImageURLIsInvalid() {

            var imageLocations = new ImageLocations(List.of(IMAGE_LOCATION));

            setupMockServer(ExpectedCount.once(), MediaType.IMAGE_GIF, BAD_REQUEST);
            when(mockImageRepository.getImageLocations()).thenReturn(imageLocations);

            assertThat(imageService.getImages()).isEmpty();
            mockServer.verify();

        }

    }

    private void setupMockServer(final ExpectedCount expectedCount, final MediaType mockedMediaType, final HttpStatus mockHttpStatus) {

        mockServer.expect(expectedCount,
                requestTo(URI.create(IMAGE_LOCATION.getUrl())))
                  .andExpect(method(HttpMethod.GET))
                  .andRespond(withStatus(mockHttpStatus)
                          .contentType(mockedMediaType)
                          .body("123".getBytes())
                  );
    }

}