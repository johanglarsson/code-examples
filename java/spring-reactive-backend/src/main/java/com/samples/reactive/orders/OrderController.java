package com.samples.reactive.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@RestController
@RequiredArgsConstructor
class OrderController {

  private final OrderService orderService;

  /**
   * Annotation based Mapping
   *
   * @return a flux containing the orders.
   */
  @GetMapping("/api/orders")
  Flux<OrderDto> findAll() {
    return orderService.findAll();
  }

  @PostMapping("/api/neworder")
  Mono<OrderDto> storeOrder(@RequestBody @Valid OrderDto orderDto) {
    return orderService.store(orderDto);
  }

  Mono<ServerResponse> handleFindAll(ServerRequest request) {
    return ServerResponse.ok().body(orderService.findAll(), OrderDto.class);
  }

  /**
   * Testing of function based router approach.
   *
   * @return RouterFunction of type {@code Mono<ServerResponse>}
   */
  @Bean
  RouterFunction<?> routerFunction() {
    return route(GET("/api/route/orders"), this::handleFindAll);
  }
}
