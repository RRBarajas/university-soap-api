package com.choice.university.util;

import static com.choice.university.util.UtilitiesForTest.getAmenity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.choice.university.service.model.Amenity;
import org.junit.jupiter.api.Test;

class UniversityUtilsTest {

  @Test
  void shouldParseBetweenObjectAnJson_whenValidInput() {
    var amenity = getAmenity();

    var parsedJson = UniversityUtils.toJson(amenity);
    assertThat(parsedJson).as("JSON should not be empty")
        .isNotNull().isNotEmpty();

    var parsedObject = UniversityUtils.fromJson(parsedJson, Amenity.class);
    assertThat(parsedObject).as("Object should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(amenity);
  }

  @Test
  void shouldThrowRuntimeException_whenPassingInvalidJson() {
    var clazz = Amenity.class;
    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(
            () -> UniversityUtils.fromJson("{ invalid json", clazz)
        ).withMessageContaining(
            String.format("Unable to create Class '%s' from JSON", clazz.getName()));
  }
}