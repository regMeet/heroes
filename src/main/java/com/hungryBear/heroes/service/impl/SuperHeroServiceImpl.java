package com.hungryBear.heroes.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicated;
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
    log.info("Getting all superheroes");
    return heroRepository.findAll();
  }

  @Override
  public SuperHero getSuperHeroById(Long id) throws SuperHeroNotFoundException {
    return heroRepository.findById(id).orElseThrow(() -> {
      log.info("Hero not found with id: {}. Method name: {}", id, "getSuperHeroById");
      return new SuperHeroNotFoundException();
    });
  }

  @Override
  public List<SuperHero> getSuperHeroByName(String name) throws SuperHeroNotFoundException {
    return heroRepository.findByNameIgnoreCaseContaining(name).orElseThrow(() -> {
      log.info("Hero not found with name: {}. Method name: {}", name, "getSuperHeroByName");
      return new SuperHeroNotFoundException();
    });
  }

  @Override
  public SuperHero saveSuperHero(SuperHeroRequest superhero) throws SuperHeroDuplicated {
    String name = superhero.getName();
    log.info("Saving a new superhero using name {}", name);
    this.checkIfHeroNameExists(name);
    return heroRepository.save(new SuperHero(name));
  }

  private void checkIfHeroNameExists(String name) throws SuperHeroDuplicated {
    if (heroRepository.findByName(name).isPresent()) {
      log.info("Duplicated superhero using name {}", name);
      throw new SuperHeroDuplicated();
    }
  }

}
