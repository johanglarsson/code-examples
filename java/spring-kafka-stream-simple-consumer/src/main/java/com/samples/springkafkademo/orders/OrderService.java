package com.samples.springkafkademo.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  /** Mapstruct for mapping Dto to model object * */
  private final OrderMapper orderMapper;

  /**
   * Make sure it is safeguarded with try catch. If the stream throws an unhandled exception then it
   * stops the stream client.
   *
   * @param key Kafka stream key
   * @param orderDto transfer object containing the order.
   */
  public void storeOrder(String key, OrderDto orderDto) {
    try {
      val order = orderMapper.orderDtoToOrder(orderDto);
      orderRepository.save(order);
      log.info("Order {} saved to database", order);
    } catch (Exception e) {
      log.error("Unable to store order into database. Do something nice, eg send to DLQ");
    }
  }
}
