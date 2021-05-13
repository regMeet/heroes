package com.hungryBear.heroes.controller.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;

public interface SuperHeroController {

  @GetMapping("/ping/{message}")
  public static String ping(@PathVariable("message") String message) {
    return message;
  }

  @GetMapping()
  public List<SuperHero> getSuperHeroes();

  @GetMapping("/id/{id}")
  public SuperHero getSuperHeroById(@PathVariable("id") Long id) throws SuperHeroNotFoundException;

  @GetMapping("/name/{name}")
  public List<SuperHero> getSuperHeroByName(@PathVariable("name") String name) throws SuperHeroNotFoundException;

}
