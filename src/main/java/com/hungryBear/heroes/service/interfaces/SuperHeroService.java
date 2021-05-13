package com.hungryBear.heroes.service.interfaces;

import java.util.List;

import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicated;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;

public interface SuperHeroService {

  public List<SuperHero> getSuperHeroes();

  public SuperHero getSuperHeroById(Long id) throws SuperHeroNotFoundException;

  public List<SuperHero> getSuperHeroByName(String name) throws SuperHeroNotFoundException;

  public SuperHero saveSuperHero(String name) throws SuperHeroDuplicated;

  public SuperHero updateSuperHero(Long id, String name) throws SuperHeroDuplicated, SuperHeroNotFoundException;

  public void deleteSuperHero(Long id) throws SuperHeroNotFoundException;

}
