# Introduction

Sample project for Kafka using Mapstruct as DTO mapper.

To send messages to the kafka topics just navivate to http://localhost:8080 and click on the links. Each click will
generate an event into Kafka.

## Install Kafka on MacOS

I'm using Brew so it was installed and started as below:

```
$ brew install kafka
$ zkServer start
$ kafka-server-start /usr/local/etc/kafka/server.properties
```

## Use KafkaCat

```brew install kafkacat```

Run a listener on a topic
```kafkacat -b localhost:9092 -t pageView-out-0```

## Implementations findings

* @KafkaListener needs to have property spring.kafka.listener.concurrency set to a value higher than number of
  partitions. Otherwise only one partition is read.
* Lombok JSON deserialization on immutable objects and Lombok. We need to annotate it as follows: @AllArgsConstructor(
  onConstructor = @__(@JsonCreator)), see OrderDto and a lombok.config file
  with ```lombok.anyConstructor.addConstructorProperties = true```to enable it.
* Mapstruct interface need to have spring ```componentModel = "spring"```
* Kafka streams creates in and out topics automatically if the the dependency is on the classpath:

```
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-stream-binder-kafka</artifactId>
  <version>3.1.1</version>
</dependency>
```

AND if we have a bean that returns a Function<>. The name of the bean will be the topic names. In our example we have
pageView so Spring will automatically create the following topics:
pageView-in-0 pageView-out-0

So when something is published in pagevView-in-0 then that function automatically runs and binds it to the output topic.