package com.app.mega.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimestamp {
  private LocalDateTime dateTime;

  public DateTimestamp(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public static DateTimestamp fromString(String dateTimeString, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString, formatter);
    return new DateTimestamp(parsedDateTime);
  }
}
