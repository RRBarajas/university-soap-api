package com.choice.university.service.impl;

import com.choice.university.repository.AmenityRepository;
import com.choice.university.service.AmenityService;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.AmenityMapper;
import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityResponse;
import org.springframework.stereotype.Service;

@Service
public class AmenityServiceJpa implements AmenityService {

  private final AmenityRepository repository;
  private final AmenityMapper mapper;

  public AmenityServiceJpa(AmenityRepository repository,
      AmenityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public GetAmenityResponse getAmenity(Long id) {
    var amenity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Amenity", id));

    return mapper.mapToGetAmenityResponse(amenity);
  }

  @Override
  public GetAmenitiesResponse getAllAmenities() {
    var allAmenities = repository.findAll();

    return mapper.mapToGetAmenitiesResponse(allAmenities);
  }
}
