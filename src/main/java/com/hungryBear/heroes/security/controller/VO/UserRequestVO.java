package com.hungryBear.heroes.security.controller.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequestVO {
  @NotNull(message = "heroes.login.username.null")
  @Size(min = 4, max = 40, message = "heroes.login.username.length")
  private String username;

  @NotNull(message = "heroes.login.password.null")
  @Size(min = 4, max = 40, message = "heroes.login.password.length")
  private String password;
}
