package com.kafkasamples.streamtask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@Slf4j
@EnableTask
@SpringBootApplication
public class StreamTaskApplication {

    public static void main(String[] args) {
        SingletonInstance instance = SingletonInstance.getInstance();
        log.info(instance.toString());

        SingletonInstance instance2 = SingletonInstance.getInstance();
        log.info(instance2.toString());
        SpringApplication.run(StreamTaskApplication.class, args);
    }

    @Bean
    CommandLineRunner testTask() {
        return args -> Arrays.stream(args).forEach(a -> log.info("Argument: {}", a));
    }

}
