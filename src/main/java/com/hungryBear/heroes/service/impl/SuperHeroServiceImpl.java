package com.hungryBear.heroes.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicatedException;
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
  public SuperHero saveSuperHero(String name) throws SuperHeroDuplicatedException {
    this.checkIfHeroNameExists(name);
    log.info("Saving a new superhero using name {}", name);
    return heroRepository.save(new SuperHero(name));
  }

  @Override
  @Transactional
  public SuperHero updateSuperHero(Long id, String newName)
      throws SuperHeroDuplicatedException, SuperHeroNotFoundException {
    SuperHero superHero = this.getSuperHeroById(id);
    if (superHero.getName().equals(newName)) {
      return superHero;
    }
    this.checkIfHeroNameExists(newName);
    log.info("Updating existent superhero; id: {}, name {}", id, newName);

    superHero.setName(newName);
    heroRepository.save(superHero);
    return superHero;
  }

  private void checkIfHeroNameExists(String name) throws SuperHeroDuplicatedException {
    if (heroRepository.findByName(name).isPresent()) {
      log.info("Duplicated superhero using name {}", name);
      throw new SuperHeroDuplicatedException();
    }
  }

  @Override
  public void deleteSuperHero(Long id) throws SuperHeroNotFoundException {
    SuperHero superHero = this.getSuperHeroById(id);
    log.info("Deleting superhero id: {}", id);
    heroRepository.delete(superHero);
  }

}
