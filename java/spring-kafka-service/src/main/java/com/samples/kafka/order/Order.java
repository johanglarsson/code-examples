package com.samples.kafka.order;

import java.util.UUID;
import lombok.Value;

@Value
public class Order {

  String id;

  String name;

  Integer quantity;

  Integer price;

  String offset;

  Order addOffset(String offset) {
    return new Order(id, name, quantity, price, offset);
  }

  Order addOrderId() {
    return new Order(UUID.randomUUID().toString(), name, quantity, price, "");
  }
}
