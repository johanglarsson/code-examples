package com.samples.kafka.product;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topic {

  public static final String PAGEVIEW_TRACKER_TOPIC_NAME = "pageViewTracker";
  public static final String CATEGORY_COUNTER_TOPIC_NAME = "categoryCounter";

  @Bean
  public NewTopic pageViewTrackerTopic() {
    return TopicBuilder.name(Topic.PAGEVIEW_TRACKER_TOPIC_NAME).partitions(1).replicas(1).build();
  }

  @Bean
  public NewTopic categoryCounterTopic() {
    return TopicBuilder.name(Topic.CATEGORY_COUNTER_TOPIC_NAME).partitions(1).replicas(1).build();
  }
}
