package com.samples.reactive.orders;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock OrderRepository orderRepository;

  @Mock OrderMapper orderMapper;

  @Test
  void findAll_willReturnAList() {
    val orderService = new OrderService(orderRepository, orderMapper);

    when(orderRepository.findAll()).thenReturn(Flux.just(orders(), orders(), orders()));
    when(orderMapper.orderToOrderDto(any(Order.class))).thenReturn(orderDto());

    // Use StepVerifier to verify reactive flow.
    StepVerifier.create(orderService.findAll())
        .expectNext(orderDto(), orderDto(), orderDto())
        .verifyComplete();
  }

  private Order orders() {
    return new Order("test", "test", 1, 99);
  }

  private OrderDto orderDto() {
    return OrderDto.builder().productName("test").text("test").price(99).quantity(1).build();
  }
}
