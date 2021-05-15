package com.hungryBear.heroes.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hungryBear.heroes.security.common.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);
}