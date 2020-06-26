package se.samples.images;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.samples.images.entities.Setting;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ImageConfigTest {

    @Autowired
    Setting setting;

    @Test
    void givenAppStartup_shouldAddAllSettings() {
        assertThat(setting.getLobsExternalUrl()).isNotNull();
        assertThat(setting.getLobsClasspathFile()).isNotNull();
    }

}