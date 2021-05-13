package com.hungryBear.heroes.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicatedException;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;
import com.hungryBear.heroes.controller.interfaces.SuperHeroController;
import com.hungryBear.heroes.service.interfaces.SuperHeroService;
import com.hungryBear.heroes.utils.LogExecutionTime;

@RestController
public class SuperHeroControllerImpl implements SuperHeroController {

  @Autowired
  private SuperHeroService superHeroService;

  @Override
  @LogExecutionTime
  public List<SuperHero> getSuperHeroes() {
    return superHeroService.getSuperHeroes();
  }

  @Override
  @LogExecutionTime
  public SuperHero getSuperHeroById(Long id) throws SuperHeroNotFoundException {
    return superHeroService.getSuperHeroById(id);
  }

  @Override
  @LogExecutionTime
  public List<SuperHero> getSuperHeroByName(String name) throws SuperHeroNotFoundException {
    return superHeroService.getSuperHeroByName(name);
  }

  @Override
  @LogExecutionTime
  public SuperHero saveSuperHero(SuperHeroRequest superhero) throws SuperHeroDuplicatedException {
    return superHeroService.saveSuperHero(superhero.getName());
  }

  @Override
  @LogExecutionTime
  public SuperHero updateHero(Long id, SuperHeroRequest superhero)
      throws SuperHeroDuplicatedException, SuperHeroNotFoundException {
    return superHeroService.updateSuperHero(id, superhero.getName());
  }

  @Override
  @LogExecutionTime
  public ResponseEntity<Void> deleteHero(Long id) throws SuperHeroNotFoundException {
    superHeroService.deleteSuperHero(id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
