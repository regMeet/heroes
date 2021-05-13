package com.hungryBear.heroes.common.errors.exceptions;

import com.hungryBear.heroes.common.errors.ErrorCode;

public class SuperHeroDuplicated extends SuperHeroException {
  private static final long serialVersionUID = 4144693686889130891L;

  public SuperHeroDuplicated() {
    super(ErrorCode.SUPER_HERO_DUPLICATED);
  }

}
