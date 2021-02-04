package com.samples.kafka.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
  private String id;
  private String category;
  private String name;
  private String imageName;
}
