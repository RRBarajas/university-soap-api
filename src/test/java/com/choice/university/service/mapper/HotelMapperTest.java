package com.choice.university.service.mapper;

import static com.choice.university.util.UtilitiesForTest.getHotel;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelMapperTest {

  @Spy
  AmenityMapper amenityMapper = Mappers.getMapper(AmenityMapper.class);

  @InjectMocks
  HotelMapper mapper = Mappers.getMapper(HotelMapper.class);

  @Test
  void shouldMapBetweenHotels_whenPassingValidObject() {
    var hotelModel = getHotel();

    var hotelEntity = mapper.mapToHotelEntity(hotelModel);
    var response = mapper.mapToGetHotelResponse(hotelEntity);

    assertThat(response).as("Response should not be null")
        .isNotNull();
    assertThat(response.getHotel()).as("Hotel should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(hotelModel);
  }

  @Test
  void shouldMapGetHotelsResponse_whenPassingValidEntityList() {
    var hotelModel = getHotel();
    var hotelEntities = List.of(mapper.mapToHotelEntity(hotelModel));

    var response = mapper.mapToGetHotelsResponse(hotelEntities);
    assertThat(response).as("Response should not be null")
        .isNotNull();

    var responseHotels = response.getHotels();
    assertThat(responseHotels).as("Hotels should not be null")
        .isNotNull();
    assertThat(responseHotels.getHotel()).as("Hotels should match the original")
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(List.of(hotelModel));
  }
}