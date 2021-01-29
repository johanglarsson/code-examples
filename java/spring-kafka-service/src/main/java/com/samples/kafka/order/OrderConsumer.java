package com.samples.kafka.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class OrderConsumer {

  private final OrderMapper orderMapper;

  @KafkaListener(id = "myGroup", groupId = "mygroup", topics = "${topic.name}")
  void mySecondTopiListener(
      @Payload final OrderDto orderDto, @Header(KafkaHeaders.OFFSET) String offset) {
    val order = orderMapper.orderDtoToOrder(orderDto);
    val orderWithId = order.addOrderId().addOffset(offset);
    log.info("Received order {} with offset {}", orderWithId, offset);
  }
}
