package com.hungryBear.heroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class HeroesApplication {

  public static void main(String[] args) {
    SpringApplication.run(HeroesApplication.class, args);
  }

  @Configuration
  @EnableCaching
  public class CacheConfig {
  }

}
