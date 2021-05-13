package com.hungryBear.heroes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.hungryBear.heroes.common.errors.ErrorCode;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroDuplicatedException;
import com.hungryBear.heroes.common.errors.exceptions.SuperHeroNotFoundException;
import com.hungryBear.heroes.common.persistence.entities.SuperHero;
import com.hungryBear.heroes.repository.SuperHeroRepository;
import com.hungryBear.heroes.service.impl.SuperHeroServiceImpl;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceTest {

  private static final String HERO_NAME = "Batman";
  private static final SuperHero superHero = new SuperHero(1L, HERO_NAME);
  private static final SuperHero superHero2 = new SuperHero(1L, "Superman");
  private static final List<SuperHero> superHeroList = Arrays.asList(superHero, superHero2);

  @InjectMocks
  SuperHeroServiceImpl superHeroService;

  @Mock
  SuperHeroRepository heroRepository;

  @Test
  public void testGetAll() {
    when(heroRepository.findAll()).thenReturn(superHeroList);
    List<SuperHero> list = superHeroService.getSuperHeroes();
    verify(heroRepository, times(1)).findAll();
    assertNotNull(list);
  }

  @Test
  public void testFindById() throws SuperHeroNotFoundException {
    when(heroRepository.findById(any())).thenReturn(Optional.of(superHero));
    SuperHero foundHero = superHeroService.getSuperHeroById(superHero.getId());
    verify(heroRepository, times(1)).findById(any());
    assertEquals(foundHero.getId(), superHero.getId());
    assertNotNull(foundHero);
  }

  @Test
  public void testFindByIdError() {
    when(heroRepository.findById(any())).thenReturn(Optional.empty());
    SuperHeroNotFoundException e = assertThrows(SuperHeroNotFoundException.class,
        () -> superHeroService.getSuperHeroById(superHero.getId()), "Expected getSuperHeroById() to throw");

    assertEquals(ErrorCode.SUPER_HERO_NOT_FOUND.getCode(), e.getMessage());
    verify(heroRepository, times(1)).findById(any());
  }

  @Test
  public void testFindByName() throws SuperHeroNotFoundException {
    when(heroRepository.findByNameIgnoreCaseContaining(any())).thenReturn(Optional.of(superHeroList));
    List<SuperHero> heroes = superHeroService.getSuperHeroByName(superHero.getName());
    verify(heroRepository, times(1)).findByNameIgnoreCaseContaining(any());
    assertNotNull(heroes);
  }

  @Test
  public void testSave() throws SuperHeroDuplicatedException {
    when(heroRepository.findByName(any())).thenReturn(Optional.empty());
    when(heroRepository.save(any())).thenReturn(new SuperHero());
    SuperHero hero = superHeroService.saveSuperHero(superHero.getName());
    verify(heroRepository, times(1)).findByName(any());
    verify(heroRepository, times(1)).save(any());
    assertNotNull(hero);
  }

  @Test
  public void testSaveError() {
    when(heroRepository.findByName(any())).thenReturn(Optional.of(superHero));

    SuperHeroDuplicatedException e = assertThrows(SuperHeroDuplicatedException.class,
        () -> superHeroService.saveSuperHero(superHero.getName()), "Expected saveSuperHero() to throw");

    assertEquals(ErrorCode.SUPER_HERO_DUPLICATED.getCode(), e.getMessage());
    verify(heroRepository, times(1)).findByName(any());
    verify(heroRepository, times(0)).save(any());
  }

  @Test
  public void testUpdate() throws SuperHeroDuplicatedException, SuperHeroNotFoundException {
    when(heroRepository.findByName(any())).thenReturn(Optional.empty());
    when(heroRepository.findById(any())).thenReturn(Optional.of(superHero));
    when(heroRepository.save(any())).thenReturn(superHero);
    superHeroService.updateSuperHero(1L, "Alan");
    verify(heroRepository, times(1)).findByName(any());
    verify(heroRepository, times(1)).findById(any());
    verify(heroRepository, times(1)).save(any());
  }

  @Test
  public void testUpdateNotFoundError() {
    when(heroRepository.findByName(any())).thenReturn(Optional.empty());
    when(heroRepository.findById(any())).thenReturn(Optional.empty());

    SuperHeroNotFoundException e = assertThrows(SuperHeroNotFoundException.class,
        () -> superHeroService.updateSuperHero(1L, superHero2.getName()), "Expected updateSuperHero() to throw");

    assertEquals(ErrorCode.SUPER_HERO_NOT_FOUND.getCode(), e.getMessage());
    verify(heroRepository, times(1)).findByName(any());
    verify(heroRepository, times(1)).findById(any());
    verify(heroRepository, times(0)).save(any());
  }

  @Test
  public void testUpdateDuplicatedError() {
    when(heroRepository.findByName(any())).thenReturn(Optional.of(superHero));

    SuperHeroDuplicatedException e = assertThrows(SuperHeroDuplicatedException.class,
        () -> superHeroService.updateSuperHero(1L, superHero2.getName()), "Expected updateSuperHero() to throw");

    assertEquals(ErrorCode.SUPER_HERO_DUPLICATED.getCode(), e.getMessage());
    verify(heroRepository, times(1)).findByName(any());
    verify(heroRepository, times(0)).findById(any());
    verify(heroRepository, times(0)).save(any());
  }

  @Test
  public void testDelete() throws SuperHeroNotFoundException {
    when(heroRepository.findById(any())).thenReturn(Optional.of(superHero));
    superHeroService.deleteSuperHero(1L);
    verify(heroRepository, times(1)).findById(any());
    verify(heroRepository, times(1)).delete(any());
  }

  @Test
  public void testDeleteNotFoundError() {
    when(heroRepository.findById(any())).thenReturn(Optional.empty());

    SuperHeroNotFoundException e = assertThrows(SuperHeroNotFoundException.class,
        () -> superHeroService.deleteSuperHero(1L), "Expected deleteSuperHero() to throw");

    assertEquals(ErrorCode.SUPER_HERO_NOT_FOUND.getCode(), e.getMessage());
    verify(heroRepository, times(1)).findById(any());
    verify(heroRepository, times(0)).delete(any());
  }

}
