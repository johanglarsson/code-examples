package com.samples.kafka.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCounter {

  private String category;
  private Long count;
}
