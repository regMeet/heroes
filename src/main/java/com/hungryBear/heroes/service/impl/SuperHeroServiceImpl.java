package com.hungryBear.heroes.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;
import com.hungryBear.heroes.repository.SuperHeroRepository;
import com.hungryBear.heroes.service.interfaces.SuperHeroService;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SuperHeroRepository heroRepository;

  @Override
  public List<SuperHero> getSuperHeroes() {
    return heroRepository.findAll();
  }

  @Override
  public SuperHero getSuperHeroById(Long id) throws SuperHeroNotFoundException {
    return heroRepository.findById(id).orElseThrow(() -> {
      log.info(String.format("Hero not found with id: %d. Method name: %s", id, "getSuperHeroById"));
      return new SuperHeroNotFoundException();
    });
  }

  @Override
  public List<SuperHero> getSuperHeroByName(String name) throws SuperHeroNotFoundException {
    return heroRepository.findByNameIgnoreCaseContaining(name).orElseThrow(() -> {
      log.info(String.format("Hero not found with name: %s. Method name: %s", name, "getSuperHeroByName"));
      return new SuperHeroNotFoundException();
    });
  }

}
