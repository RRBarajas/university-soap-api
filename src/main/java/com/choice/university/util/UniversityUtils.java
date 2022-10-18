package com.choice.university.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class UniversityUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private UniversityUtils() {
  }

  /**
   * Parse an Object to JSON. Mainly used to print objects when debugging.
   *
   * @return String with the object in JSON format
   */
  public static <T> String toJson(T object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(
          String.format("Unable to create JSON from Class '%s'", object.getClass().getName()));
    }
  }

  /**
   * Parse a JSON into an Object. Inverse method from the above, but not really used.
   *
   * @return The object representing the JSON
   */
  public static <T> T fromJson(String object, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(object, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(
          String.format("Unable to create Class '%s' from JSON", clazz.getName()));
    }
  }
}
