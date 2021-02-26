package com.samples.reactive.orders;

import com.samples.reactive.validation.NotBadWord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
class OrderDto {

  private String productName;

  @NotBadWord(badWords = {"fuck", "fan"})
  private String text;

  @Min(1)
  @Max(10)
  private int quantity;

  @Min(1)
  private double price;
}
