package com.choice.university.service;

import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;

public interface HotelService {

  GetHotelResponse getHotel(Long id);

  GetHotelsResponse getHotelsByName(String name, int pageNumber, int pageSize);

  GetHotelResponse createHotel(CreateHotel hotel);

  void deleteHotel(Long id);

  GetHotelResponse addAmenitiesToHotel(Long hotelId, Amenities amenities);

  GetHotelResponse removeAmenitiesFromHotel(Long hotelId, Amenities amenities);
}
