package com.hungryBear.heroes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hungryBear.heroes.common.persistence.entities.SuperHero;

@Repository
public interface SuperHeroRepository extends CrudRepository<SuperHero, Long> {

  List<SuperHero> findAll();

  Optional<SuperHero> findByName(String name);

  Optional<List<SuperHero>> findByNameIgnoreCaseContaining(String name);

}