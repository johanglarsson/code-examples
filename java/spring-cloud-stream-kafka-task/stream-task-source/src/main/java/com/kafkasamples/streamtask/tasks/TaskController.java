package com.kafkasamples.streamtask.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskProcessor t;

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public @ResponseBody
    String launchTask(@RequestBody String s) {
        t.publisStartTask(s);
        log.info("Message published");
        return "success";
    }
}
