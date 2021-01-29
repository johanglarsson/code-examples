package com.samples.kafka.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class OrderPublisher {

  private final KafkaTemplate<String, OrderDto> kafkaTemplate;
  private final TopicProperties topicProperties;

  void publish(final OrderDto orderDto) {
    log.info("About to publish order:: {}", orderDto);
    kafkaTemplate
        .send(topicProperties.getName(), orderDto.getProduct(), orderDto)
        .addCallback(this::successCallback, this::errorCallback);
  }

  private void successCallback(SendResult<String, OrderDto> sendResult) {
    log.info(
        "Published {} to Kafka partition {}",
        sendResult.getProducerRecord().value(),
        sendResult.getRecordMetadata().partition());
  }

  private void errorCallback(Throwable t) {
    log.error("Unable to publish", t);
  }
}
