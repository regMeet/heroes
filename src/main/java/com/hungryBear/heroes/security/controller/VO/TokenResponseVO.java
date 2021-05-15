package com.hungryBear.heroes.security.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseVO {
  private final String token;
}
