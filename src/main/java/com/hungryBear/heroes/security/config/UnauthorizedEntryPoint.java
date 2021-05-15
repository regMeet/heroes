package com.hungryBear.heroes.security.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hungryBear.heroes.common.VO.ErrorVO;
import com.hungryBear.heroes.common.errors.ErrorCode;

/**
 * This class will extend Spring's AuthenticationEntryPoint class and override its method commence. It rejects every
 * unauthenticated request and send error code 401
 */
@Component
public final class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {
  private static final long serialVersionUID = 7496821190952249788L;
  private static final String APPLICATION_JSON = "application/json";

  /**
   * http://stackoverflow.com/questions/19767267/handle-spring-security-authentication-exceptions-with-exceptionhandler
   * The ExceptionHandler will only work if the request is handled by the DispatcherServlet. However this exception
   * occurs before that as it is thrown by a Filter.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    response.setContentType(APPLICATION_JSON);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ErrorVO error = new ErrorVO(ErrorCode.AUTH_UNAUTHORIZED.getCode());

    response.getWriter().println(new Gson().toJson(error));
  }
}