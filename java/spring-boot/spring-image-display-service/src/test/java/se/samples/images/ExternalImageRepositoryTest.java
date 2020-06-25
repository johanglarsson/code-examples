package se.samples.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import java.net.MalformedURLException;
import java.net.URI;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalImageRepositoryTest {

    private static final String TEST_URL = "http://localhost:9000/test";
    private static final String TEST_PAGE = "123";

    @Mock
    RestTemplate mockRestTemplate;

    @Mock
    ConversionService mockConversionService;

    @Mock
    Setting mockSetting;

    @Mock
    ResponseEntity<String> mockResponseEntity;

    ImageRepository imageRepository;

    @BeforeEach
    void init() throws MalformedURLException {
        when(mockSetting.getLobsExternalUrl()).thenReturn(URI.create(TEST_URL).toURL());
        imageRepository = new ExternalImageRepository(mockSetting, mockConversionService, mockRestTemplate);
    }

    @Test
    void givenValidURL_shouldReturnImageLocations() {
        when(mockRestTemplate.getForEntity(TEST_URL, String.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getBody()).thenReturn(TEST_PAGE);
        when(mockConversionService.convert(TEST_PAGE, ImageLocations.class)).thenReturn(mock(ImageLocations.class));

        imageRepository.getImageLocations();

        verify(mockRestTemplate).getForEntity(TEST_URL, String.class);
        verify(mockConversionService).convert(TEST_PAGE, ImageLocations.class);
    }

}