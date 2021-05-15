package com.hungryBear.heroes.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.common.errors.exceptions.HttpAuthConflictException;
import com.hungryBear.heroes.common.errors.exceptions.HttpAuthUnauthorizedException;
import com.hungryBear.heroes.security.common.entities.User;
import com.hungryBear.heroes.security.repository.UserRepository;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String login(String username, String password) throws HttpAuthUnauthorizedException {
    authenticate(username, password);
    return tokenManager.createToken(username);
  }

  private void authenticate(String emailOrUsername, String password) throws HttpAuthUnauthorizedException {
    Authentication authentication = new UsernamePasswordAuthenticationToken(emailOrUsername, password);
    try {
      authentication = authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      // if the credentials are wrong
      throw new HttpAuthUnauthorizedException();
    }
  }

  public String signup(String username, String password) throws HttpAuthConflictException {
    Optional<User> foundUser = userRepository.findByUsername(username);
    if (!foundUser.isPresent()) {
      User user = new User(username, passwordEncoder.encode(password), User.USER_ROLE);
      userRepository.save(user);
      return tokenManager.createToken(username);
    }
    throw new HttpAuthConflictException();
  }
}
