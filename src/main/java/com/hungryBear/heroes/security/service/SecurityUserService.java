package com.hungryBear.heroes.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.security.common.entities.User;
import com.hungryBear.heroes.security.common.entities.UserContext;
import com.hungryBear.heroes.security.repository.UserRepository;

/**
 * Implements Spring Security {@link UserDetailsService} that is injected into authentication provider in configuration.
 * It interacts with domain, loads user details and wraps it into {@link UserContext} which implements Spring Security
 * {@link UserDetails}.
 */
@Service
public class SecurityUserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Spring will calls on AuthenticationManager when using authenticate method.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> foundUser = userRepository.findByUsername(username);

    if (foundUser.isPresent()) {
      return new UserContext(foundUser.get());
    }

    throw new UsernameNotFoundException("User " + username + " not found");
  }

}
