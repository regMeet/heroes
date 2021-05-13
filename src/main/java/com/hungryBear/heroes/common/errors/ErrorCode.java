package com.hungryBear.heroes.common.errors;

public enum ErrorCode {
  SUPER_HERO_NOT_FOUND("superheroes.hero.not.found"),
  SUPER_HERO_DUPLICATED("superheroes.hero.duplicated"),
  INTERNAL_ERROR("superheroes.internal.error");

  private final String messageKey;

  ErrorCode(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessageKey() {
    return messageKey;
  }

}
