package se.samples.images;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"app.useExternalRepository = false"})
class ClassPathResourceImageRepositoryComponentTest {

    @Autowired
    Setting setting;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void givenValidSetup_shouldReturnImageLocations() {
        System.out.println(setting);
        final ImageLocations imageLocations = imageRepository.getImageLocations();
        assertThat(imageLocations.getImageLocations()).hasSize(3);
    }


}