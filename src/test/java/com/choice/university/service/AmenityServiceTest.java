package com.choice.university.service;

import static com.choice.university.util.UtilitiesForTest.getAmenityEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.choice.university.repository.AmenityRepository;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.AmenityMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AmenityServiceTest {

  @Spy
  private AmenityMapper mapper = Mappers.getMapper(AmenityMapper.class);

  @Mock
  private AmenityRepository repository;

  @InjectMocks
  private AmenityService service;

  @Test
  void shouldThrowEntityNotFoundException_whenNotFindingEntityId() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () -> service.getAmenity(1L)
        ).withMessageContaining("Amenity '1' does not exist");
  }

  @Test
  void shouldReturnGetAmenityResponse_whenRequestingValidId() {
    var amenityEntity = getAmenityEntity();

    when(repository.findById(anyLong())).thenReturn(Optional.of(amenityEntity));

    var response = service.getAmenity(1L);
    assertThat(response).as("Response should not be null")
        .isNotNull();
    assertThat(response.getAmenity()).as("Amenity should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(amenityEntity);
  }

  @Test
  void shouldReturnEmptyAmenities_whenNotFindingAnything() {
    when(repository.findAll()).thenReturn(List.of());

    var response = service.getAllAmenities();
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var amenities = response.getAmenities();
    assertThat(amenities).as("Amenities should not be null")
        .isNotNull();
    assertThat(amenities.getAmenity()).as("Amenity list should be empty")
        .isEmpty();
  }

  @Test
  void shouldReturnValidAmenitiesList_whenFindingSomething() {
    var amenityEntityList = List.of(getAmenityEntity());
    when(repository.findAll()).thenReturn(amenityEntityList);

    var response = service.getAllAmenities();
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var amenities = response.getAmenities();
    assertThat(amenities).as("Amenities should not be null")
        .isNotNull();
    assertThat(amenities.getAmenity()).as("Amenity list should match original")
        .isNotNull()
        .hasSize(amenityEntityList.size())
        .usingRecursiveComparison()
        .isEqualTo(amenityEntityList);
  }
}