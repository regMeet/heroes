package com.hungryBear.heroes.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class LocalDateUtils {
  public static DateTime getTodayDateTime() {
    return new DateTime(DateTimeZone.UTC);
  }
}
