package com.choice.university.service.impl;

import static com.choice.university.util.UtilitiesForTest.getAmenities;
import static com.choice.university.util.UtilitiesForTest.getAmenitiesWithDuplicates;
import static com.choice.university.util.UtilitiesForTest.getCreateHotel;
import static com.choice.university.util.UtilitiesForTest.getHotel;
import static com.choice.university.util.UtilitiesForTest.getHotelEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.choice.university.repository.HotelRepository;
import com.choice.university.service.exception.AlreadyExistsException;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.AmenityMapper;
import com.choice.university.service.mapper.HotelMapper;
import com.choice.university.service.model.Amenities;
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
class HotelServiceJpaTest {

  @Spy
  private AmenityMapper amenityMapper = Mappers.getMapper(AmenityMapper.class);

  @Spy
  @InjectMocks
  private HotelMapper mapper = Mappers.getMapper(HotelMapper.class);

  @Mock
  private HotelRepository repository;

  @InjectMocks
  private HotelServiceJpa service;

  @Test
  void shouldThrowEntityNotFoundException_whenHotelDoesntExist() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () -> service.getHotel(1L)
        ).withMessageContaining("Hotel '1' does not exist");
  }

  @Test
  void shouldReturnGetHotelResponse_whenRequestingValidId() {
    var hotelEntity = getHotelEntity();
    when(repository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));

    var hotelModel = getHotel();
    var response = service.getHotel(1L);
    assertThat(response).as("Response should not be null")
        .isNotNull();
    assertThat(response.getHotel()).as("Hotel should match the expected")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(hotelModel);
  }

  @Test
  void shouldReturnEmptyHotelList_whenNotFindingAnything() {
    when(repository.findAllByNameContainingIgnoreCase(any())).thenReturn(List.of());

    var response = service.getHotelsByName("Random text");
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var hotels = response.getHotels();
    assertThat(hotels).as("Hotels should not be null")
        .isNotNull();
    assertThat(hotels.getHotel()).as("Hotel list should be empty")
        .isEmpty();
  }

  @Test
  void shouldReturnValidAmenitiesList_whenFindingSomething() {
    var hotelEntityList = List.of(getHotelEntity());
    when(repository.findAllByNameContainingIgnoreCase(any())).thenReturn(hotelEntityList);

    var response = service.getHotelsByName("Any given input");
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var hotelModelList = List.of(getHotel());
    var hotels = response.getHotels();
    assertThat(hotels).as("Hotels should not be null")
        .isNotNull();
    assertThat(hotels.getHotel()).as("Hotel list should match original")
        .isNotNull()
        .hasSize(hotelModelList.size())
        .usingRecursiveComparison()
        .isEqualTo(hotelModelList);
  }

  @Test
  void shouldThrowAlreadyExistsException_whenHotelAlreadyExists() {
    when(repository.existsById(anyLong())).thenReturn(true);

    assertThatExceptionOfType(AlreadyExistsException.class)
        .isThrownBy(
            () -> service.createHotel(getCreateHotel())
        ).withMessageContaining("Hotel '1' already exists");
  }

  @Test
  void shouldReturnGetHotelResponse_whenCreatingNewHotel() {
    when(repository.save(any())).thenAnswer(a -> a.getArguments()[0]);

    var hotelToCreate = getCreateHotel();
    var response = service.createHotel(hotelToCreate);
    assertThat(response).as("Response should not be null")
        .isNotNull();
    assertThat(response.getHotel()).as("Hotel should match the expected")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(hotelToCreate);
  }

  @Test
  void shouldThrowEntityNotFoundException_whenDeletingInvalidHotel() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(
            () -> service.deleteHotel(1L)
        ).withMessageContaining("Hotel '1' does not exist");
  }

  @Test
  void shouldPassWithoutProblems_whenDeletingValidHotel() {
    when(repository.existsById(anyLong())).thenReturn(true);

    var idToDelete = 1L;
    service.deleteHotel(idToDelete);

    verify(repository).existsById(idToDelete);
    verify(repository).existsById(idToDelete);
  }

  @Test
  void shouldReturnUnmodifiedHotel_whenPassingDuplicatesAmenities() {
    var hotelEntity = getHotelEntity();
    when(repository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));
    when(repository.save(any())).thenAnswer(a -> a.getArguments()[0]);

    var newAmenities = getAmenitiesWithDuplicates();
    var response = service.addAmenitiesToHotel(1L, newAmenities);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseHotel = response.getHotel();
    assertThat(responseHotel).as("Hotel should not be null")
        .isNotNull();

    var hotelAmenities = responseHotel.getAmenities();
    assertThat(hotelAmenities).as("Amenities should not be null")
        .isNotNull();

    var amenitiesList = hotelAmenities.getAmenity();
    assertThat(amenitiesList).as("Amenities list should be the exact same")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(hotelEntity.getAmenities());
  }

  @Test
  void shouldReturnHotelWithNewAmenities_whenPassingNewValues() {
    var hotelEntity = getHotelEntity();
    when(repository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));
    when(repository.save(any())).thenAnswer(a -> a.getArguments()[0]);

    var newAmenities = getAmenities();
    var firstAmenity = newAmenities.getAmenity().get(0);
    firstAmenity.setId(firstAmenity.getId() + 1);
    var response = service.addAmenitiesToHotel(1L, newAmenities);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseHotel = response.getHotel();
    assertThat(responseHotel).as("Hotel should not be null")
        .isNotNull();

    var hotelAmenities = responseHotel.getAmenities();
    assertThat(hotelAmenities).as("Amenities should not be null")
        .isNotNull();

    var amenitiesList = hotelAmenities.getAmenity();
    assertThat(amenitiesList).as("Amenities list should be one item bigger")
        .isNotNull()
        .hasSize(newAmenities.getAmenity().size() + 1);
  }

  @Test
  void shouldReturnHotelWithoutAmenities_whenRemovingExistingAmenities() {
    var hotelEntity = getHotelEntity();
    when(repository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));
    when(repository.save(any())).thenAnswer(a -> a.getArguments()[0]);

    var amenitiesToRemove = getAmenitiesWithDuplicates();
    var response = service.removeAmenitiesFromHotel(1L, amenitiesToRemove);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseHotel = response.getHotel();
    assertThat(responseHotel).as("Hotel should not be null")
        .isNotNull();

    var hotelAmenities = responseHotel.getAmenities();
    assertThat(hotelAmenities).as("Amenities should not be null")
        .isNotNull();

    assertThat(hotelAmenities.getAmenity()).as("Amenities list should be empty")
        .isNotNull()
        .isEmpty();
  }

  @Test
  void shouldReturnUnmodifiedHotel_whenRemovingEmptyAmenities() {
    var hotelEntity = getHotelEntity();
    when(repository.findById(anyLong())).thenReturn(Optional.of(hotelEntity));
    when(repository.save(any())).thenAnswer(a -> a.getArguments()[0]);

    var amenitiesToRemove = new Amenities();
    var response = service.removeAmenitiesFromHotel(1L, amenitiesToRemove);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseHotel = response.getHotel();
    assertThat(responseHotel).as("Hotel should not be null")
        .isNotNull();

    var hotelAmenities = responseHotel.getAmenities();
    assertThat(hotelAmenities).as("Amenities should not be null")
        .isNotNull();

    var amenitiesList = hotelAmenities.getAmenity();
    assertThat(amenitiesList).as("Amenities list should be the exact same")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(hotelEntity.getAmenities());
  }
}