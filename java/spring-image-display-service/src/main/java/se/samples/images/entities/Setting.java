package se.samples.images.entities;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.net.URL;

@Data
public class Setting {

    private boolean useExternalRepository;

    private URL lobsExternalUrl;

    private Resource lobsClasspathFile;

}
