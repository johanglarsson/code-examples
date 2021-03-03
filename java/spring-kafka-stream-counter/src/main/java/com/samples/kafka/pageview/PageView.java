package com.samples.kafka.pageview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageView {

  private String categoryId;
  private String userAgent;
  private LocalDateTime timestamp;

  public static PageView from(final String category, final String userAgent) {
    return new PageView(category, userAgent, LocalDateTime.now());
  }
}
