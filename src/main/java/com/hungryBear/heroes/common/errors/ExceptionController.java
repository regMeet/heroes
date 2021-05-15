package com.hungryBear.heroes.common.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.VO.ErrorVO;
import com.hungryBear.heroes.common.errors.exceptions.HttpAuthConflictException;
import com.hungryBear.heroes.common.errors.exceptions.HttpAuthUnauthorizedException;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicatedException;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;

@ControllerAdvice
public class ExceptionController {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(SuperHeroNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorVO userNotFound(SuperHeroNotFoundException e) {
    return new ErrorVO(e.getMessage());
  }

  @ExceptionHandler(SuperHeroDuplicatedException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorVO userDuplicated(SuperHeroDuplicatedException e) {
    return new ErrorVO(e.getMessage());
  }

  @ExceptionHandler({ HttpAuthUnauthorizedException.class })
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public @ResponseBody ErrorVO authenticateAccessToken(HttpAuthUnauthorizedException e) {
    return new ErrorVO(e.getMessage());
  }

  @ExceptionHandler({ HttpAuthConflictException.class })
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public @ResponseBody ErrorVO existentAccount(HttpAuthConflictException e) {
    return new ErrorVO(e.getMessage());
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public @ResponseBody ErrorVO accessDenied() {
    return new ErrorVO(ErrorCode.AUTH_UNAUTHORIZED.getCode());
  }

  @ExceptionHandler({ Exception.class })
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ErrorVO catchRestOfExceptions(Exception e) {
    log.error("An exception has occurred", e);
    return new ErrorVO(ErrorCode.INTERNAL_ERROR.getCode());
  }

}
