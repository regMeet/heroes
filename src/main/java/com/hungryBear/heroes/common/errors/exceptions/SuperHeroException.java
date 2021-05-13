package com.hungryBear.heroes.common.errors.exceptions;

import com.hungryBear.heroes.common.errors.ErrorCode;

public class SuperHeroException extends Exception {
  private static final long serialVersionUID = 2038927382144436927L;

  private ErrorCode errorCode;

  public SuperHeroException(ErrorCode errorCode) {
    super(errorCode.getMessageKey());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
