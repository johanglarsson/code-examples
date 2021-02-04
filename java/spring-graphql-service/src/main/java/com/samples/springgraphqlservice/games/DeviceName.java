package com.samples.springgraphqlservice.games;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeviceName {
  PS4("Playstation 4"),
  PS5("Playstation 5"),
  XBOX("Xbox"),
  PC("PC");

  private final String name;
}
