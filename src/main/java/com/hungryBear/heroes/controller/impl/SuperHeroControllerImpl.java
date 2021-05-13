package com.hungryBear.heroes.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;
import com.hungryBear.heroes.controller.interfaces.SuperHeroController;
import com.hungryBear.heroes.service.interfaces.SuperHeroService;

@RestController
public class SuperHeroControllerImpl implements SuperHeroController {

  @Autowired
  private SuperHeroService superHeroService;

  @Override
  public List<SuperHero> getSuperHeroes() {
    return superHeroService.getSuperHeroes();
  }

  @Override
  public SuperHero getSuperHeroById(Long id) throws SuperHeroNotFoundException {
    return superHeroService.getSuperHeroById(id);
  }

  @Override
  public List<SuperHero> getSuperHeroByName(String name) throws SuperHeroNotFoundException {
    return superHeroService.getSuperHeroByName(name);
  }

}
