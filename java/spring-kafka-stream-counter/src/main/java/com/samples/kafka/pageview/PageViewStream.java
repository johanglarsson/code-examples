package com.samples.kafka.pageview;

import com.samples.kafka.product.ProductCounter;
import com.samples.kafka.product.ProductCounterTransformer;
import lombok.extern.slf4j.Slf4j;
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
    return input ->
        input
            .peek((k, v) -> log.info("Received {} into stream", v))
            .mapValues(
                p ->
                    PageView.from(
                        p.getCategoryId(), "Testing simple transformation of message value"))
            .peek((k, v) -> log.info("We have {} after simple value transform}", v))
            .groupByKey()
            .count()
            .toStream()
            .peek((k, v) -> log.info("New stream message with category {} and count {}", k, v))
            .transform(ProductCounterTransformer::new);
  }
}
