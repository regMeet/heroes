package com.hungryBear.heroes.security.common.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
  public static final String USER_ROLE = "USER";
  
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NonNull
  @Column(name = "username")
  private String username;

  @NonNull
  @Column(name = "password")
  private String password;

  @NonNull
  @Column(name = "role")
  private String role;

}
