package com.samples.kafka.home;

import com.samples.kafka.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final ProductRepository productRepository;

  @RequestMapping(value = "/")
  public String home(final Model model) {
    model.addAttribute("products", productRepository.findAll());
    return "index";
  }
}
