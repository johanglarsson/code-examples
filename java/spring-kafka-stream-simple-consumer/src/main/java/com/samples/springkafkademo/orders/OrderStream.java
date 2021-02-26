package com.samples.springkafkademo.orders;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderStream {

  private final OrderService orderService;
  private static final String TOPIC_NAME = "my.inbound";

  @Bean
  NewTopic myInboundTopic() {
    return TopicBuilder.name(TOPIC_NAME).partitions(2).replicas(1).build();
  }

  @Bean
  Consumer<KStream<String, OrderDto>> process() {
    return stream -> stream.foreach(orderService::storeOrder);
  }
}
