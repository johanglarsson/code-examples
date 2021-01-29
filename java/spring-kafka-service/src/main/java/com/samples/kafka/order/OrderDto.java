package com.samples.kafka.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
// Must be used to JSON deserialize
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class OrderDto {

  String id;

  String product;

  Integer quantity;

  Integer price;

  // String offset;
}
