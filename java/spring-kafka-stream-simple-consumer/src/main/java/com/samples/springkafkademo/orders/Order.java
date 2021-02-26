package com.samples.springkafkademo.orders;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "orders")
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String productName;

  @Getter @Setter private Long quantity;
  @Getter @Setter private Long price;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id)
        && Objects.equals(productName, order.productName)
        && Objects.equals(quantity, order.quantity)
        && Objects.equals(price, order.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productName, quantity, price);
  }
}
