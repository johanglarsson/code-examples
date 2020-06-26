package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

@Repository
@ConditionalOnProperty(name = "app.useExternalRepository", havingValue = "true")
@AllArgsConstructor
@Slf4j
public class ExternalImageRepository implements ImageRepository, InitializingBean {

    private final Setting setting;

    private final ConversionService conversionService;

    private final RestTemplate restTemplate;

    @Override
    public ImageLocations getImageLocations() {
        return Try.of(() -> restTemplate.getForEntity(setting.getLobsExternalUrl().toString(), String.class))
                  .map(HttpEntity::getBody)
                  .map(page -> conversionService.convert(page, ImageLocations.class))
                  .onSuccess(imageLocations -> log.info("Retrieved from external repository {}", imageLocations))
                  .getOrElseThrow(e -> new RuntimeException("Unable to retrieve repository from external resource " + setting
                          .getLobsExternalUrl().toString()));
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Initialized external repository with setting {}", setting.getLobsExternalUrl());
    }
}
