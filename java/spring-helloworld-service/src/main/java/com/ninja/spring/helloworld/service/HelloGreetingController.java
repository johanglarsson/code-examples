package com.ninja.spring.helloworld.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author johanlarsson
 */
@RestController
public class HelloGreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public HelloGreeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new HelloGreeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
}
