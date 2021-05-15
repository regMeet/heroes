package com.hungryBear.heroes.security.common.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This object wraps User class and makes it to UserDetails so that Spring Security can use it.
 */
public class UserContext implements UserDetails {
  private static final long serialVersionUID = -3619196115136310030L;
  private User user;

  public UserContext(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    String role = user.getRole();
    authorities.add(new SimpleGrantedAuthority(role));

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
