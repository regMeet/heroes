package com.hungryBear.heroes.common.errors;

public enum ErrorCode {
  SUPER_HERO_NOT_FOUND("superheroes.hero.not.found"),
  SUPER_HERO_DUPLICATED("superheroes.hero.duplicated"),
  AUTH_UNAUTHORIZED("superheroes.unauthorized.access"),
  AUTH_CONFLICT_ACCOUNT("superheroes.username.conflict"),
  INTERNAL_ERROR("superheroes.internal.error");

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
