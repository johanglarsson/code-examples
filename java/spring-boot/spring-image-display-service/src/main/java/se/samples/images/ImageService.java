package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.Image;
import se.samples.images.entities.ImageLocation;
import se.samples.images.entities.ImageLocations;
import se.samples.images.entities.Setting;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService {

    private final Setting setting;

    private final ImageRepository externalImageRepository;

    private final ImageRepository classPathResourceImageRepository;

    private final RestTemplate restTemplate;

    public List<Image> getImages() {
        var imageLocations = setting.getUseExternalLobsService() ?
                externalImageRepository.getImageLocations() :
                classPathResourceImageRepository.getImageLocations();
        log.info("Loading images from external sources");
        var images = getAllImages(imageLocations);
        log.info("[{}] images loaded", images.size());
        return images;

    }

    private List<Image> getAllImages(final ImageLocations imageLocations) {
        return imageLocations.getImageLocations().stream()
                             .map(this::retrieveExternalImage)
                             .collect(toList())
                             .stream()
                             .map(CompletableFuture::join)
                             .filter(Objects::nonNull)
                             .collect(toList());
    }

    private CompletableFuture<Image> retrieveExternalImage(ImageLocation imageLocation) {
        return CompletableFuture
                .supplyAsync(() -> Try.of(() -> restTemplate.getForEntity(imageLocation.getUrl(), byte[].class))
                                      .filter(this::isImage)
                                      .onSuccess(image -> log.info("Loaded {}", image))
                                      .map(responseEntity -> createImage(imageLocation, responseEntity))
                                      .getOrNull());
    }

    private boolean isImage(ResponseEntity<byte[]> responseEntity) {
        return responseEntity.getHeaders().getContentType().getType().contains("image");
    }

    private Image createImage(final ImageLocation imageLocation, final ResponseEntity<byte[]> response) {
        return Image.builder().caption(imageLocation.getCaption())
                    .url(imageLocation.getUrl())
                    .contentType(response.getHeaders().getContentType().toString())
                    .imageData(Base64.encodeBase64String(response.getBody()))
                    .build();
    }

}
