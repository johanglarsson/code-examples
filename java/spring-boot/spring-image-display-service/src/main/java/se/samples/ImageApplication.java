package se.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.thymeleaf.spring5.view.ThymeleafView;

@SpringBootApplication
public class ImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);
    }


    @Bean(name = "content-part")
    @Scope("prototype")
    public ThymeleafView someViewBean() {
        ThymeleafView view = new ThymeleafView("index");
        view.setMarkupSelector("images");
        return view;
    }

}
