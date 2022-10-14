
package com.choice.university.service.mapper;

import com.choice.university.entity.Hotel;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;
import com.choice.university.service.model.Hotels;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AmenityMapper.class})
public interface HotelMapper {

  com.choice.university.service.model.Hotel mapToHotel(Hotel hotel);

  Hotel mapToHotelEntity(com.choice.university.service.model.Hotel hotel);

  @Mapping(source = ".", target = "hotel")
  GetHotelResponse mapToGetHotelResponse(Hotel hotel);

  default GetHotelsResponse mapToGetHotelsResponse(List<Hotel> hotels) {
    if (hotels == null) {
      return null;
    }

    var response = new GetHotelsResponse();
    response.setHotels(mapToHotels(hotels));
    return response;
  }

  default Hotels mapToHotels(List<Hotel> hotels) {
    var responseHotels = new Hotels();
    responseHotels.getHotel().addAll(
        hotels.stream()
            .map(this::mapToHotel)
            .toList());
    return responseHotels;
  }
}
