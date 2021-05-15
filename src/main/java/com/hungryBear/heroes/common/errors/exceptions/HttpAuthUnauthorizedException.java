package com.hungryBear.heroes.common.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.errors.ErrorCode;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class HttpAuthUnauthorizedException extends SuperHeroException {
  private static final long serialVersionUID = -8257103944235355450L;

  public HttpAuthUnauthorizedException() {
    super(ErrorCode.AUTH_UNAUTHORIZED);
  }

}