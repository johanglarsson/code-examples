package com.samples.springkafkademo.orders;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Test class for custom error handler of deserialization errors causing poisonous pills in the
 * stream. Like invalid format.
 */
@Slf4j
@Component
public class OrderStreamErrorHandler implements DeserializationExceptionHandler {

  @Override
  public DeserializationHandlerResponse handle(
      ProcessorContext processorContext,
      ConsumerRecord<byte[], byte[]> consumerRecord,
      Exception e) {
    log.error("Poisonous message, cannot deserialize", e);
    return DeserializationHandlerResponse.CONTINUE;
  }

  @Override
  public void configure(Map<String, ?> configs) {}
}
