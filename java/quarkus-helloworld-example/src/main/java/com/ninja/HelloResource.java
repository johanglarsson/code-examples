package com.ninja;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Path("/resteasy/hello")
public class HelloResource {

    @Autowired
    private HelloService helloService;

    @Autowired
    @Qualifier("capitalizer")
    private StringFunction capitalizer;

    @Autowired
    @Qualifier("lowercase")
    private StringFunction lowercase;

    @Value("${greeting.extra-message}")
    private String extraMessage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HelloModel hello() {
        helloService.sayHello();
        return new HelloModel(capitalizer.apply("hello " + extraMessage));
    }
}