package com.kafkasamples.streamtask.tasks;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
public class TaskProcessor {

    @Value("${demo.task-url}")
    private String taskUrl;

    private StreamBridge streamBridge;

    void publisStartTask(final String payload) {
        final String taskUrl = "maven://com.kafkasamples:stream-task:jar:0.0.1-SNAPSHOT";
        final List<String> payloadAsList = Arrays.asList(payload.split(","));
        // Produce a TaskLaunchRequest with the payload.
        final TaskLaunchRequest request = new TaskLaunchRequest(taskUrl, payloadAsList, null, null, null);
        final GenericMessage<TaskLaunchRequest> message = new GenericMessage<>(request);
        // Publish to stream using dynamic streambridge.
        streamBridge.send("tasktopic", message);
    }

    /**
     * Stream consumer and the destination is mapped in applicstion.properties. This is just to show
     * how the function based approach of creating consumers on streams work.
     *
     * @return a consumer of the message containing @see {@link TaskLaunchRequest}
     */
    @Bean
    Consumer<TaskLaunchRequest> process() {
        return input -> log.info("Received task command {}", input.getUri());
    }


}
