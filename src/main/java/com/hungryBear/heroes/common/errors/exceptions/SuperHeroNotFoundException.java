package com.hungryBear.heroes.common.errors.exceptions;

import com.hungryBear.heroes.common.errors.ErrorCode;

public class SuperHeroNotFoundException extends SuperHeroException {
  private static final long serialVersionUID = -7084651306005282525L;

  public SuperHeroNotFoundException() {
    super(ErrorCode.SUPER_HERO_NOT_FOUND);
  }

}
