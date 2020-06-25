package se.samples.images.entities;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
public class Image {

    String caption;

    String url;

    String contentType;

    String imageData;

    @ToString.Include
    String imageData() {
        return imageData != null ? String.valueOf(imageData.length()) : "0";
    }
}
