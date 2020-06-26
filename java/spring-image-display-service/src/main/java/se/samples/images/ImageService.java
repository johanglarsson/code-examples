package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.samples.images.entities.Image;
import se.samples.images.entities.ImageLocation;
import se.samples.images.entities.ImageLocations;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final RestTemplate restTemplate;

    public List<Image> getImages() {
        log.info("Loading images from external sources");
        var images = getAllImages(imageRepository.getImageLocations());
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
                .supplyAsync(() -> Try
                        .of(() -> restTemplate
                                .exchange(URI.create(imageLocation.getUrl()), GET, httpHeaders(), byte[].class))
                        .filter(this::hasContentType)
                        .filter(this::isImage)
                        .onSuccess(image -> log.debug("Loaded {}", image.getHeaders()))
                        .map(responseEntity -> createImage(imageLocation, responseEntity))
                        .onFailure(error -> log.info("Image could not be loaded", error))
                        .getOrNull());
    }

    private HttpEntity<HttpHeaders> httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache, no-store, must-revalidate, max-age=0");
        return new HttpEntity<>(headers);
    }

    private boolean hasContentType(ResponseEntity<byte[]> response) {
        return response.getHeaders().getContentType() != null;
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
