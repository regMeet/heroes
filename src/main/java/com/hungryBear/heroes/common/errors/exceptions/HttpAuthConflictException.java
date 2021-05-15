package com.hungryBear.heroes.common.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.errors.ErrorCode;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class HttpAuthConflictException extends SuperHeroException {
  private static final long serialVersionUID = 1L;

  public HttpAuthConflictException() {
    super(ErrorCode.AUTH_CONFLICT_ACCOUNT);
  }

}