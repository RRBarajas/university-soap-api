
package com.choice.university.service.mapper;

import com.choice.university.entity.Amenity;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

  com.choice.university.service.model.Amenity mapToAmenity(Amenity amenity);

  Amenity mapToAmenityEntity(com.choice.university.service.model.Amenity amenity);

  List<Amenity> mapToAmenityEntityList(List<com.choice.university.service.model.Amenity> amenities);

  @Mapping(source = ".", target = "amenity")
  GetAmenityResponse mapToGetAmenityResponse(Amenity amenity);

  default GetAmenitiesResponse mapToGetAmenitiesResponse(List<Amenity> amenities) {
    if (amenities == null) {
      return null;
    }

    var response = new GetAmenitiesResponse();
    response.setAmenities(mapToAmenities(amenities));
    return response;
  }

  default Amenities mapToAmenities(List<Amenity> amenities) {
    var responseAmenities = new Amenities();
    responseAmenities.getAmenity().addAll(
        amenities.stream()
            .map(this::mapToAmenity)
            .toList());
    return responseAmenities;
  }

  default List<Amenity> mapToAmenitiesList(Amenities amenities) {
    return amenities.getAmenity().stream()
        .map(this::mapToAmenityEntity)
        .toList();
  }

  default Set<Long> getAmenitiesIds(List<Amenity> amenities) {
    return amenities.stream()
        .map(Amenity::getId)
        .collect(Collectors.toSet());
  }
}
