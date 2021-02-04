package com.samples.kafka;

import com.samples.kafka.home.TopicProperties;
import com.samples.kafka.pageview.PageView;
import com.samples.kafka.product.ProductCounter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class KafkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaApplication.class, args);
  }

  @Bean
  public NewTopic pageViewIn(TopicProperties topicProperties) {
    return TopicBuilder.name(topicProperties.getName())
        .partitions(topicProperties.getNumOfPartitions())
        .replicas(topicProperties.getNumOfReplicas())
        .build();
  }

  @Bean
  public NewTopic pageViewOut() {
    return TopicBuilder.name("categoryCounter").partitions(1).replicas(1).build();
  }

  /**
   * Kafka stream which will take the input topic defined in application.yaml and produce an
   * aggregate of count per category into a KTable with Category - Count
   *
   * @return new output which is published to the Kafka output destination in the application.yaml.
   */
  @Bean
  public Function<KStream<String, PageView>, KStream<String, ProductCounter>> process() {

    return input ->
        input
            .peek((k, v) -> log.info("Received a category {} into Kafka stream", v.getCategoryId()))
            .groupByKey()
            .count()
            .toStream()
            .peek((k, v) -> log.info("New stream with key {} and counter {} ", k, v))
            .map((key, value) -> new KeyValue<>(key, new ProductCounter(key, value)));
  }
}
