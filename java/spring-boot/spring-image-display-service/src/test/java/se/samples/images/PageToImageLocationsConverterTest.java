package se.samples.images;

import io.vavr.control.Try;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.samples.images.entities.ImageLocations;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class PageToImageLocationsConverterTest {

    private static final PageToImageLocationsConverter CONVERTER = new PageToImageLocationsConverter();

    @Nested
    class givenDataTableOk {

        @Test
        void shouldCreateImageLocations_whenManyLocationsPresent() {
            final ImageLocations imageLocations = CONVERTER.convert(getTestData("example-with-many.txt"));
            assertThatLocationsHaveCorrectSize(imageLocations, 3);
        }

        @Test
        void shouldCreateImageLocations_whenOnlyOneLocationPresent() {

            final ImageLocations imageLocations = CONVERTER.convert(getTestData("example-with-one.txt"));
            assertThatLocationsHaveCorrectSize(imageLocations, 1);
        }
    }

    @Nested
    class givenDataTableCorrupt {

        @Test
        void shouldReturnEmptyImageLocations_whenDataTableIsEmpty() {
            final ImageLocations imageLocations = CONVERTER.convert(getTestData("example-with-none.txt"));
            assertThatLocationsHaveCorrectSize(imageLocations, 0);
        }

        @Test
        void shouldReturnEmptyImageLocations_whenDataTableIsMissing() {
            final ImageLocations imageLocations = CONVERTER.convert(getTestData("example-with-notable.txt"));
            assertThatLocationsHaveCorrectSize(imageLocations, 0);
        }

        @Test
        void shouldCreateDefaultCaption_whenCaptionIsMissing() {
            final ImageLocations imageLocations = CONVERTER.convert(getTestData("example-with-missing-caption.txt"));
            assertThatLocationsHaveCorrectSize(imageLocations, 1);

        }

    }


    private void assertThatLocationsHaveCorrectSize(final ImageLocations imageLocations, final Integer expectedSize) {
        assertThat(imageLocations).isNotNull();
        Assertions.assertThat(imageLocations.getImageLocations()).hasSize(expectedSize);
    }

    private String getTestData(final String testfile) {
        return Try.of(() -> Files.readAllBytes(Path.of("src", "test", "resources").resolve(testfile)))
                  .map(String::new)
                  .getOrElseThrow(e -> new RuntimeException("Unable to read test file", e));
    }

}
