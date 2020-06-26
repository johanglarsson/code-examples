package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import java.nio.file.Files;

@Repository
@ConditionalOnProperty(name = "app.useExternalRepository", havingValue = "false")
@AllArgsConstructor
@Slf4j
public class ClassPathResourceImageRepository implements ImageRepository, InitializingBean {

    private final Setting setting;

    private final ConversionService conversionService;

    @Override
    public ImageLocations getImageLocations() {
        return Try.of(() -> Files.readAllBytes(setting.getLobsClasspathFile().getFile().toPath()))
                  .map(String::new)
                  .map(page -> conversionService.convert(page, ImageLocations.class))
                  .onSuccess(imageLocations -> log.info("Retrieved from classpath repository {}", imageLocations))
                  .getOrElseThrow(e -> new RuntimeException("Unable to retrieve local classpath resource", e));
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Initialized classpath repository with setting {}", setting.getLobsClasspathFile());
    }

}
