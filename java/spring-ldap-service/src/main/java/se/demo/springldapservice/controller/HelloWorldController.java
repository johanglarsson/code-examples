package se.demo.springldapservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {


    @GetMapping
    public String sayHi() {
        return "Hello World";
    }
}
