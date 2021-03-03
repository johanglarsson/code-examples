package com.samples.kafka.product;

import com.samples.kafka.pageview.PageView;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
public class PageViewStream {

  /**
   * Kafka stream which will take the input topic defined in application.yaml and produce an
   * aggregate of count per category into a KTable with Category - Count. See bindings section.
   *
   * @return new output which is published to the Kafka output destination in the application.yaml.
   */
  @Bean
  public Function<KStream<String, PageView>, KStream<String, ProductCounter>> process() {
    log.info("Starting stream consumer");
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
