package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

@Service("externalImageRepository")
@AllArgsConstructor
public class ExternalImageRepository implements ImageRepository {

    private final Setting setting;

    private final ConversionService conversionService;

    private final RestTemplate restTemplate;

    @Override
    public ImageLocations getImageLocations() {
        return Try.of(() -> restTemplate.getForEntity(setting.getLobsExternalUrl().toString(), String.class))
                  .map(HttpEntity::getBody)
                  .map(page -> conversionService.convert(page, ImageLocations.class))
                  .getOrElseThrow(e -> new RuntimeException("Unable to retrieve Lobs external resource"));
    }
}
