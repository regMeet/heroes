package com.hungryBear.heroes.controller.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicated;
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

  @PostMapping()
  public SuperHero saveSuperHero(@RequestBody @Valid SuperHeroRequest superhero) throws SuperHeroDuplicated;

  @PutMapping("/{id}")
  public SuperHero updateHero(@PathVariable("id") Long id, @RequestBody @Valid SuperHeroRequest request)
      throws SuperHeroDuplicated, SuperHeroNotFoundException;
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHero(@PathVariable("id") Long id) throws SuperHeroNotFoundException;
  

}
