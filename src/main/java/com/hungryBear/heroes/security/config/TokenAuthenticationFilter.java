package com.hungryBear.heroes.security.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hungryBear.heroes.common.errors.exceptions.HttpAuthUnauthorizedException;
import com.hungryBear.heroes.security.service.SecurityUserService;
import com.hungryBear.heroes.security.service.TokenManager;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_HEADER = "Bearer ";

  @Autowired
  private SecurityUserService securityUserService;

  @Autowired
  private TokenManager tokenManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authToken = request.getHeader(AUTHORIZATION_HEADER);

    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
    if (authToken != null) {
      if (authToken.startsWith(BEARER_HEADER)) {
        jwtToken = authToken.substring(7);
        try {
          username = tokenManager.getUsernameFromToken(jwtToken);
        } catch (HttpAuthUnauthorizedException e) {
          log.info("There was an error with the token specified in the request. Message: {}", e.getMessage());
        }
      } else {
        log.warn("JWT Token does not begin with Bearer String");
      }
    }

    // Once we get the token validated.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (username != null && authentication == null) {
      UserDetails userDetails = this.securityUserService.loadUserByUsername(username);

      Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, authorities);
      // After setting the Authentication in the context, we specify that the current user is authenticated. So it
      // passes the Spring Security Configurations successfully.
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    chain.doFilter(request, response);
  }

}
