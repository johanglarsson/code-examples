package com.samples.kafka.home;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "topic")
@ConstructorBinding
@Value
public class TopicProperties {

  String name;
  Integer numOfPartitions;
  Integer numOfReplicas;
}
