package com.samples.kafka.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderPublisher orderPublisherService;

  @Operation(summary = "New message", description = "Operation for producing a Kafka message")
  @ApiResponse(responseCode = "200", description = "Message produced")
  @RequestMapping(method = RequestMethod.POST, value = "/api/v1/neworder")
  public void newOrder(@RequestBody OrderDto order) {
    orderPublisherService.publish(order);
  }
}
