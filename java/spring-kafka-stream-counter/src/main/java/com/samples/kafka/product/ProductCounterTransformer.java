package com.samples.kafka.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

@Slf4j
public class ProductCounterTransformer
    implements Transformer<String, Long, KeyValue<String, ProductCounter>> {

  private ProcessorContext processorContext;

  @Override
  public void init(ProcessorContext processorContext) {
    this.processorContext = processorContext;
  }

  @Override
  public KeyValue<String, ProductCounter> transform(String s, Long count) {
    log.info(
        "Received message from {} and partition {}",
        processorContext.topic(),
        processorContext.partition());
    return KeyValue.pair(s, new ProductCounter(s, count));
  }

  @Override
  public void close() {}
}
