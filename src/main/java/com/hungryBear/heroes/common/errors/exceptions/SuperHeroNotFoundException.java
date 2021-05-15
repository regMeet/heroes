package com.hungryBear.heroes.common.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.errors.ErrorCode;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SuperHeroNotFoundException extends SuperHeroException {
  private static final long serialVersionUID = -7084651306005282525L;

  public SuperHeroNotFoundException() {
    super(ErrorCode.SUPER_HERO_NOT_FOUND);
  }

}
