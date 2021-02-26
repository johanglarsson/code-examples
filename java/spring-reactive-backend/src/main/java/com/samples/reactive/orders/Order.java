package com.samples.reactive.orders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@RequiredArgsConstructor
@Getter
@ToString
public class Order {

  @MongoId private String id;

  private final String product;

  private final String comment;

  private final int quantity;

  private final double price;
}
