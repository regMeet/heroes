package com.hungryBear.heroes.utils;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
    log.info("Cache event CREATED for item with key {}. Old value = {}, New value = {}", cacheEvent.getKey(),
        cacheEvent.getOldValue(), cacheEvent.getNewValue());
  }
}
