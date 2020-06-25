package se.samples.images;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.ImageLocations;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExternalImageRepositoryComponentTest {

    @MockBean
    private RestTemplate mockRestTemplate;

    @Mock
    ResponseEntity<String> mockResponseEntity;

    @Autowired
    private ImageRepository externalImageRepository;

    @Test
    void givenLobsServiceExist_shouldReturnImageLocations() {
        when(mockRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getBody()).thenReturn(mockedResult());
        final ImageLocations imageLocations = externalImageRepository.getImageLocations();
        assertThat(imageLocations.getImageLocations()).hasSize(3);
    }


    private String mockedResult() {
        final ClassPathResource classPathResource = new ClassPathResource("example-with-many.txt");
        return Try.of(() -> Files.readAllBytes(classPathResource.getFile().toPath()))
                  .map(String::new)
                  .getOrElseThrow(e -> new RuntimeException("Unable to find test file", e));
    }

}