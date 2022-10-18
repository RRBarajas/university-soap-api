package com.choice.university.service;

import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityResponse;

public interface AmenityService {

  GetAmenityResponse getAmenity(Long id);

  GetAmenitiesResponse getAllAmenities();
}
