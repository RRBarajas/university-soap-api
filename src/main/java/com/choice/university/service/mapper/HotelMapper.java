
package com.choice.university.service.mapper;

import com.choice.university.entity.Hotel;
import com.choice.university.service.model.GetHotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AmenityMapper.class})
public interface HotelMapper {

  @Mapping(source = ".", target = "hotel")
  GetHotelResponse mapToGetHotelResponse(Hotel hotel);
}
