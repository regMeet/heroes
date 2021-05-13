package com.hungryBear.heroes.common.VO.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SuperHeroRequest {

  @NotNull(message = "superheroes.new.user.name.null")
  @Size(min = 1, max = 80, message = "superheroes.new.user.lenght")
  private String name;

}
