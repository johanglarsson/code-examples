package com.samples.kafka.product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

  public List<Product> findAll() {
    final List<Product> products = new ArrayList<>();
    products.add(
        Product.builder()
            .name("Samsung Galaxy")
            .category("1")
            .id("1")
            .imageName("samsung-galaxy.png")
            .build());
    products.add(
        Product.builder()
            .name("Iphone 12")
            .category("1")
            .id("2")
            .imageName("iphone-12.png")
            .build());
    products.add(
        Product.builder()
            .name("Sony Xperia 5")
            .category("1")
            .id("3")
            .imageName("sony-xperia-5.png")
            .build());
    products.add(
        Product.builder()
            .name("Samsung Tv")
            .category("2")
            .id("4")
            .imageName("samsung-tv.jpg")
            .build());
    products.add(
        Product.builder().name("LG Tv").category("2").id("5").imageName("lg-tv.jpg").build());
    products.add(
        Product.builder()
            .name("Playstation 5")
            .category("2")
            .id("6")
            .imageName("playstation5.jpg")
            .build());
    return products;
  }
}
