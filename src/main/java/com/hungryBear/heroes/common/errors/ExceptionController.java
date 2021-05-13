package com.hungryBear.heroes.common.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hungryBear.heroes.common.VO.ErrorVO;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;

@ControllerAdvice
public class ExceptionController {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(SuperHeroNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorVO userNotFound(SuperHeroNotFoundException e) {
    return new ErrorVO(e.getMessage());
  }

  @ExceptionHandler({ Exception.class })
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ErrorVO catchRestOfExceptions(Exception e) {
    log.error("An exception has occurred", e);
    return new ErrorVO(ErrorCode.INTERNAL_ERROR.getMessageKey());
  }

}
