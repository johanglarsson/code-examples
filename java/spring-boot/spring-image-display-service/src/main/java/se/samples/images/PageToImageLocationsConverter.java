package se.samples.images;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import se.samples.images.entities.ImageLocation;
import se.samples.images.entities.ImageLocations;

import java.util.Arrays;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class PageToImageLocationsConverter implements Converter<String, ImageLocations> {

    private static final String NEW_LINE_REGEXP = "\\r?\\n";
    private static final String TABLE_HEADER = "Data: <URL kommentar>";
    private static final String ROW_SPLIT_CHARACTER = "#";

    @Override
    public ImageLocations convert(String source) {
        return Try.of(() -> extractDataTable(source))
                  .map(this::removeTableHeader)
                  .map(this::toImageLocationList)
                  .get();
    }

    private ImageLocations toImageLocationList(String s) {
        var imageLocationList = Arrays.stream(s.split(NEW_LINE_REGEXP))
                                      .filter(not(this::emptyLine))
                                      .map(this::toImageLocation)
                                      .collect(toList());
        return new ImageLocations(imageLocationList);
    }

    private boolean emptyLine(final String line) {
        return line.length() < 1;
    }

    private ImageLocation toImageLocation(final String line) {
        final var urlSeparator = line.indexOf(" ");
        if (urlSeparator == -1) {
            return new ImageLocation(line, "[No caption]");
        }
        var url = line.substring(0, urlSeparator);
        var caption = line.substring(urlSeparator);
        return new ImageLocation(url, caption);
    }

    private String removeTableHeader(String e) {
        return e.trim().replace(TABLE_HEADER, "");
    }

    private String extractDataTable(final String page) {
        return Arrays.stream(page.split(ROW_SPLIT_CHARACTER))
                     .filter(a -> a.contains(TABLE_HEADER))
                     .findFirst()
                     .orElse("");
    }

}
