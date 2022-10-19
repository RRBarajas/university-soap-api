package com.choice.university.service.mapper;

import static com.choice.university.util.UtilitiesForTest.getAmenity;
import static com.choice.university.util.UtilitiesForTest.getAmenityEntity;
import static com.choice.university.util.UtilitiesForTest.getAmenityList;
import static com.choice.university.util.UtilitiesForTest.getAmenityListWithDuplicates;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


class AmenityMapperTest {

  AmenityMapper mapper = Mappers.getMapper(AmenityMapper.class);

  @Test
  void shouldMapBetweenAmenities_whenPassingValidObject() {
    var amenityModel = getAmenity();
    var amenityEntity = getAmenityEntity();

    var response = mapper.mapToGetAmenityResponse(amenityEntity);

    assertThat(response).as("Response should not be null")
        .isNotNull();
    assertThat(response.getAmenity()).as("Amenity should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(amenityEntity)
        .isEqualTo(amenityModel);
  }

  @Test
  void shouldMapToAmenityEntities_whenPassingValidList() {
    var originalAmenities = getAmenityList();

    var mappedEntities = mapper.mapToAmenityEntityList(originalAmenities);
    assertThat(mappedEntities).as("List should not be null")
        .isNotNull()
        .hasSameSizeAs(originalAmenities);

    for (int i = 0; i < originalAmenities.size(); i++) {
      assertThat(mappedEntities.get(i)).as("Object should match the original")
          .isNotNull()
          .usingRecursiveComparison()
          .isEqualTo(originalAmenities.get(i));
    }
  }

  @Test
  void shouldMapToGetAmenitiesResponse_whenPassingValidEntityList() {
    var originalAmenities = getAmenityList();
    var originalEntities = mapper.mapToAmenityEntityList(originalAmenities);

    var response = mapper.mapToGetAmenitiesResponse(originalEntities);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseAmenities = response.getAmenities();
    assertThat(responseAmenities).as("Amenities should not be null")
        .isNotNull();
    assertThat(responseAmenities.getAmenity()).as("Amenities should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(originalAmenities);

    var mappedAmenities = mapper.mapToAmenitiesList(responseAmenities);
    assertThat(mappedAmenities).as("Entities should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(originalEntities);
  }

  @Test
  void shouldMapToUniqueIds_whenPassingValidAmenityList() {
    var originalAmenities = getAmenityListWithDuplicates();
    var mappedEntities = mapper.mapToAmenityEntityList(originalAmenities);

    var mappedIds = mapper.getAmenitiesIds(mappedEntities);
    for (var amenity : mappedEntities) {
      assertThat(mappedIds).as("IDs should be unique")
          .containsOnlyOnce(amenity.getId());
    }
  }
}