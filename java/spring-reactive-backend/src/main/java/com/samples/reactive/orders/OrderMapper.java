package com.samples.reactive.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
interface OrderMapper {

  @Mappings({
    @Mapping(source = "text", target = "comment"),
    @Mapping(source = "productName", target = "product")
  })
  Order orderDtoToOrder(final OrderDto orderDto);

  @Mappings({
    @Mapping(source = "comment", target = "text"),
    @Mapping(source = "product", target = "productName")
  })
  OrderDto orderToOrderDto(final Order order);
}
