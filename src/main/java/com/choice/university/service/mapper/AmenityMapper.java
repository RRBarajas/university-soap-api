
package com.choice.university.service.mapper;

import com.choice.university.entity.Amenity;
import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

  com.choice.university.service.model.Amenity mapToAmenity(Amenity amenity);

  @Mapping(source = ".", target = "amenity")
  GetAmenityResponse mapToGetAmenityResponse(Amenity amenity);

  default GetAmenitiesResponse mapToGetAmenitiesResponse(List<Amenity> amenities) {
    if (amenities == null) {
      return null;
    }
    var response = new GetAmenitiesResponse();
    response.getAmenity().addAll(
        amenities.stream()
            .map(this::mapToAmenity)
            .toList());
    return response;
  }
}
