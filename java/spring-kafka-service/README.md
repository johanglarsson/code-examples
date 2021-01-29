
# Introduction

## Install Kafka on MacOS

I'm using Brew so it was installed and started as below:

```
$ brew install kafka
$ zkServer start
$ kafka-server-start /usr/local/etc/kafka/server.properties
```

## Implementations findings

* @KafkaListener needs to have property spring.kafka.listener.concurrency set to a value higher than number of partitions. Otherwise only one partition is read.
* Lombok JSON deserialization on immutable objects and Lombok. We need to annotate it as follows: @AllArgsConstructor(onConstructor = @__(@JsonCreator)), see OrderDto and a lombok.config file with ```lombok.anyConstructor.addConstructorProperties = true```to enable it.
* Mapstruct interface need to have spring ```componentModel = "spring"```