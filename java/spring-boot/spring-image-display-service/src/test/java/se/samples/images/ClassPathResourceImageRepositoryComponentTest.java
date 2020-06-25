package se.samples.images;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.samples.images.entities.ImageLocations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClassPathResourceImageRepositoryComponentTest {

    @Autowired
    private ImageRepository classPathResourceImageRepository;

    @Test
    void givenValidSetup_shouldReturnImageLocations() {
        final ImageLocations imageLocations = classPathResourceImageRepository.getImageLocations();
        assertThat(imageLocations.getImageLocations()).hasSize(3);
    }


}