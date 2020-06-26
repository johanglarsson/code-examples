package se.samples.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassPathResourceImageRepositoryTest {

    private static final Path TEST_FILE = Path.of("src", "test", "resources", "example-with-many.txt");

    @Mock
    ConversionService mockConversionService;

    @Mock
    Setting mockSetting;

    @Mock
    Resource mockResource;

    ImageRepository imageRepository;

    @BeforeEach
    void init() throws IOException {
        when(mockSetting.getLobsClasspathFile()).thenReturn(mockResource);
        when(mockResource.getFile()).thenReturn(TEST_FILE.toFile());
        imageRepository = new ClassPathResourceImageRepository(mockSetting, mockConversionService);
    }

    @Test
    void givenValidURL_shouldReturnImageLocations() {
        when(mockConversionService.convert(anyString(), eq(ImageLocations.class)))
                .thenReturn(mock(ImageLocations.class));

        imageRepository.getImageLocations();

        verify(mockConversionService).convert(anyString(), eq(ImageLocations.class));
    }

}