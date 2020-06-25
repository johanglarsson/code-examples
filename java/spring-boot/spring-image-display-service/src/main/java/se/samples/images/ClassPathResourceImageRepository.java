package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import java.nio.file.Files;

@Repository("classPathResourceImageRepository")
@AllArgsConstructor
public class ClassPathResourceImageRepository implements ImageRepository {

    private final Setting setting;

    private final ConversionService conversionService;

    @Override
    public ImageLocations getImageLocations() {
        return Try.of(() -> Files.readAllBytes(setting.getLobsClasspathFile().getFile().toPath()))
                  .map(String::new)
                  .map(page -> conversionService.convert(page, ImageLocations.class))
                  .getOrElseThrow(e -> new RuntimeException("Unable to retrieve local classpath resource", e));
    }


}
