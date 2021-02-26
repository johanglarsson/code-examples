package com.samples.springkafkademo.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
  private String id;
  private String name;
  private int quantity;
  private double price;
}
