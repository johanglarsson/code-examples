package com.samples.kafka.product;

import com.samples.kafka.pageview.PageView;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

  private final KafkaTemplate<String, PageView> kafkaTemplate;

  @RequestMapping("/product")
  public String product(
      @RequestParam("category") String category,
      @RequestHeader(value = "User-Agent") String userAgent) {
    // Send a pageView event to the kafka topic that the user clicked on the product.
    kafkaTemplate.send(
        Topic.PAGEVIEW_TRACKER_TOPIC_NAME, category, PageView.from(category, userAgent));
    return "redirect:/";
  }
}
