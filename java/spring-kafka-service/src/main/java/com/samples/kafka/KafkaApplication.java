package com.samples.kafka;

import com.samples.kafka.order.TopicProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class KafkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaApplication.class, args);
  }

  @Bean
  public NewTopic myTopic(TopicProperties topicProperties) {
    return TopicBuilder.name(topicProperties.getName())
        .partitions(topicProperties.getNumOfPartitions())
        .replicas(topicProperties.getNumOfReplicas())
        .build();
  }

  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  @Bean
  public OpenAPI kafkaOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Kafka API")
                .description("This is a sample API for producing messages into Kafka")
                .version("1.0")
                .license(new License().name("The Last Ninja")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Kafka API Documentation")
                .url("https://github.com/johanglarsson"));
  }
}
