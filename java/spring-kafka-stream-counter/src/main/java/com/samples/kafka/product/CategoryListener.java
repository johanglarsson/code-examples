package com.samples.kafka.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@KafkaService
public class CategoryListener {

  /**
   * Kafka consumer for the aggregated categoryCounter topic pushed from the above Kafka stream
   * function which uses pageViewTracker as input, aggregates count and pushes the result to the
   * categoryCounter topic.
   *
   * @param record containing the aggregated count for a category.
   */
  @KafkaListener(topics = Topic.CATEGORY_COUNTER_TOPIC_NAME, groupId = "category-grp")
  public void consumeCategoryCounter(ConsumerRecord<String, ProductCounter> record) {
    log.info("Receive {}", record.value());
  }
}
