package com.samples.kafka.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/** Mapper classes which uses Mapstruct to map DTO to model object. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

  @Mapping(source = "name", target = "product")
  OrderDto orderToOrderDto(Order order);

  @Mapping(source = "product", target = "name")
  Order orderDtoToOrder(OrderDto orderDto);
}
