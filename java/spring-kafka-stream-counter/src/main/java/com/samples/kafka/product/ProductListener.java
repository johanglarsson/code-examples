package com.samples.kafka.product;

import com.samples.kafka.pageview.PageView;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@KafkaService
public class ProductListener {

  /**
   * Kafka listener for the pageViewTracker which has the click events from the UI.
   *
   * @param record of the click event page view.
   */
  @KafkaListener(groupId = "pageview-grp", topics = Topic.PAGEVIEW_TRACKER_TOPIC_NAME)
  public void consumePageViewTracker(ConsumerRecord<String, PageView> record) {
    log.info("Received {}", record.value());
  }
}
