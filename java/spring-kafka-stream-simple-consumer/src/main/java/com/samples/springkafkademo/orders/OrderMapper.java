package com.samples.springkafkademo.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
interface OrderMapper {

  @Mappings(@Mapping(source = "name", target = "productName"))
  Order orderDtoToOrder(OrderDto orderDTO);
}
