package se.samples.images;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @RequestMapping("/")
    public String getImage(Model model) {
        return "index";
    }


    @RequestMapping("/images")
    public String showContentPart(final Model model) {
        log.info("All images");
        Try.of(imageService::getImages)
           .onSuccess(images -> model.addAttribute("images", images))
           .onFailure(e -> model.addAttribute("error", e.getMessage()))
           .onFailure(e -> log.error("Unable to display home page", e));
        return "index :: #images";
    }

}
