package com.samples.kafka;

import com.samples.kafka.pageview.PageView;
import com.samples.kafka.product.ProductCounter;
import com.samples.kafka.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class KafkaApplication {

  private static final String PAGEVIEW_TRACKER_TOPIC_NAME = "pageViewtracker";
  private static final String CATEGORY_COUNTER_TOPIC_NAME = "categoryCounter";

  public static void main(String[] args) {
    SpringApplication.run(KafkaApplication.class, args);
  }

  @Bean
  public NewTopic pageViewTrackerTopic() {
    return TopicBuilder.name(PAGEVIEW_TRACKER_TOPIC_NAME).partitions(1).replicas(1).build();
  }

  @Bean
  public NewTopic categoryCounterTopic() {
    return TopicBuilder.name(CATEGORY_COUNTER_TOPIC_NAME).partitions(1).replicas(1).build();
  }

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
            .peek((k, v) -> log.info("Received a category {} into Kafka stream", v.getCategoryId()))
            .groupByKey()
            .count()
            .toStream()
            .peek((k, v) -> log.info("New stream with key {} and counter {} ", k, v))
            .map((key, value) -> new KeyValue<>(key, new ProductCounter(key, value)));
  }

  /**
   * Kafka consumer for the aggregated categoryCounter topic pushed from the above Kafka stream
   * function which uses pageViewTracker as input, aggregates count and pushes the result to the
   * categoryCounter topic.
   *
   * @param record containing the aggregated count for a category.
   */
  @KafkaListener(topics = CATEGORY_COUNTER_TOPIC_NAME, groupId = "category-grp")
  public void consumeCategoryCounter(ConsumerRecord<String, ProductCounter> record) {
    log.info("Receive {}", record.value());
  }

  /**
   * Kafka listener for the pageViewTracker which has the click events from the UI.
   *
   * @param record of the click event page view.
   */
  @KafkaListener(groupId = "pageview-grp", topics = PAGEVIEW_TRACKER_TOPIC_NAME)
  public void consumePageViewTracker(ConsumerRecord<String, PageView> record) {
    log.info("Received {}", record.value());
  }

  /**
   * I took a shortcut for the Home page. Should be a separate class but for demo purposes I
   * included it in the Application class. Duh! Just to keep it simple in one page, both producer
   * and consumer
   */
  @Controller
  @RequiredArgsConstructor
  static class Home {
    private final KafkaTemplate<String, PageView> kafkaTemplate;
    private final ProductRepository productRepository;

    @RequestMapping(value = "/")
    public String home(final Model model) {
      model.addAttribute("products", productRepository.findAll());
      return "index";
    }

    @RequestMapping("/product")
    public String product(
        @RequestParam("category") String category,
        @RequestHeader(value = "User-Agent") String userAgent) {
      // Send a pageView event to the kafka topic that the user clicked on the product.
      kafkaTemplate.send(
          PAGEVIEW_TRACKER_TOPIC_NAME, category, PageView.valueOf(category, userAgent));
      return "redirect:/";
    }
  }
}
