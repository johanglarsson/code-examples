package com.kafkasamples.streamtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.launcher.annotation.EnableTaskLauncher;

@EnableTaskLauncher
@SpringBootApplication
public class StreamTaskSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamTaskSinkApplication.class, args);
    }

}
