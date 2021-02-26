package com.samples.reactive.orders;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

  private static final String PRODUCT_NAME = "Playstation 5";
  private static final String ORDER_COMMENT = "I'm loving it";
  private static final double PRICE = 199.90;
  private static final int QUANTITY = 1;

  private static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  @Test
  void orderDtoToOrder_allFieldsMappedToOrder() {
    OrderDto orderDto =
        OrderDto.builder()
            .price(PRICE)
            .quantity(QUANTITY)
            .text(ORDER_COMMENT)
            .productName(PRODUCT_NAME)
            .build();

    val order = INSTANCE.orderDtoToOrder(orderDto);
    assertThat(order.getComment()).isEqualTo(ORDER_COMMENT);
    assertThat(order.getProduct()).isEqualTo(PRODUCT_NAME);
    assertThat(order.getPrice()).isEqualTo(PRICE);
    assertThat(order.getQuantity()).isEqualTo(QUANTITY);
  }

  @Test
  void orderToOrderDto_allFieldsMappedToOrderDto() {
    Order order = new Order(PRODUCT_NAME, ORDER_COMMENT, QUANTITY, PRICE);

    val orderDto = INSTANCE.orderToOrderDto(order);
    assertThat(orderDto.getText()).isEqualTo(ORDER_COMMENT);
    assertThat(orderDto.getProductName()).isEqualTo(PRODUCT_NAME);
    assertThat(order.getPrice()).isEqualTo(PRICE);
    assertThat(order.getQuantity()).isEqualTo(QUANTITY);
  }
}
