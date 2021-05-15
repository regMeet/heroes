package com.hungryBear.heroes.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hungryBear.heroes.common.errors.exceptions.HttpAuthConflictException;
import com.hungryBear.heroes.common.errors.exceptions.HttpAuthUnauthorizedException;
import com.hungryBear.heroes.security.controller.VO.TokenResponseVO;
import com.hungryBear.heroes.security.controller.VO.UserRequestVO;
import com.hungryBear.heroes.security.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthService service;

  @PostMapping("/login")
  public @ResponseBody TokenResponseVO login(@RequestBody @Valid UserRequestVO user)
      throws HttpAuthUnauthorizedException {
    String token = service.login(user.getUsername(), user.getPassword());
    return new TokenResponseVO(token);
  }

  @PostMapping("/signup")
  public @ResponseBody TokenResponseVO signup(@RequestBody @Valid UserRequestVO user) throws HttpAuthConflictException {
    String token = service.signup(user.getUsername(), user.getPassword());
    return new TokenResponseVO(token);
  }
}
