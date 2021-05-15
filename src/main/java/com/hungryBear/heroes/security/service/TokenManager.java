package com.hungryBear.heroes.security.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.common.errors.exceptions.HttpAuthUnauthorizedException;
import com.hungryBear.heroes.utils.LocalDateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again, older token is
 * invalidated.
 */
@Service
public class TokenManager {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${spring.security.jwt.secret}")
  private String secret;

  private static final int DAY_EXPIRATION_TIME = 24; // 24 hours = 1 day
  private static final int LOGIN_EXPIRATION = 14; // days

  public String createToken(String username) {
    return createNewToken(username, DAY_EXPIRATION_TIME * LOGIN_EXPIRATION);
  }

  private String createNewToken(String username, int duration) {
    DateTime today = LocalDateUtils.getTodayDateTime();
    JwtBuilder jwtBuilder = Jwts.builder();
    jwtBuilder.setSubject(username);
    jwtBuilder.setIssuedAt(today.toDate());
    if (duration > 0) {
      jwtBuilder.setExpiration(today.plusHours(duration).toDate());
    }
    jwtBuilder.signWith(SignatureAlgorithm.HS512, secret);
    return jwtBuilder.compact();
  }

  public String getUsernameFromToken(String accessToken) throws HttpAuthUnauthorizedException {
    String username = null;
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken);
      username = claimsJws.getBody().getSubject();
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
        | IllegalArgumentException e) {
      log.info("There was an error decoding the access token " + e.getMessage());
      throw new HttpAuthUnauthorizedException();
    }

    return username;
  }

}
