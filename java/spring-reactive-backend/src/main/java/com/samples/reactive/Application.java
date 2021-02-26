package com.samples.reactive;

import com.samples.reactive.orders.Order;
import com.samples.reactive.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Slf4j
  @Component
  @RequiredArgsConstructor
  static class InitializeOrderRepository implements ApplicationRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
      val order = new Order("TestProduct", "My test comment", 23, 99.90);
      orderRepository.save(order).subscribe(s -> log.info("Order {} saved", s));
    }
  }
}
