package com.hungryBear.heroes.common.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.errors.ErrorCode;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SuperHeroDuplicatedException extends SuperHeroException {
  private static final long serialVersionUID = 4144693686889130891L;

  public SuperHeroDuplicatedException() {
    super(ErrorCode.SUPER_HERO_DUPLICATED);
  }

}
