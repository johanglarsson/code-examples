package com.samples.reactive.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
class OrderService {

  private static final int TIMEOUT_IN_SECONDS = 2;

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  Flux<OrderDto> findAll() {
    return orderRepository
        .findAll()
        .map(orderMapper::orderToOrderDto)
        .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
        .doOnError(e -> Flux.error(new RuntimeException("Unable to connect to Mongodb")));
  }

  public Mono<OrderDto> store(OrderDto orderDto) {
    return orderRepository
        .save(orderMapper.orderDtoToOrder(orderDto))
        .doOnNext(o -> log.info("Saved {} to mongo", o))
        .map(orderMapper::orderToOrderDto);
  }
}
