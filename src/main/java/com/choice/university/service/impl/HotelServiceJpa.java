package com.choice.university.service.impl;

import com.choice.university.entity.Hotel;
import com.choice.university.repository.HotelRepository;
import com.choice.university.service.HotelService;
import com.choice.university.service.exception.AlreadyExistsException;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.AmenityMapper;
import com.choice.university.service.mapper.HotelMapper;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceJpa implements HotelService {

  private final HotelRepository repository;
  private final HotelMapper mapper;
  private final AmenityMapper amenityMapper;

  public HotelServiceJpa(HotelRepository repository,
      HotelMapper mapper,
      AmenityMapper amenityMapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.amenityMapper = amenityMapper;
  }

  @Override
  public GetHotelResponse getHotel(Long id) {
    var hotel = getExistingHotel(id);

    return mapper.mapToGetHotelResponse(hotel);
  }

  @Override
  public GetHotelsResponse getHotelsByName(String name, int pageNumber, int pageSize) {
    var pagination = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
    var hotels = repository.findAllByNameContainingIgnoreCase(name, pagination);

    return mapper.mapToGetHotelsResponse(hotels);
  }

  @Override
  public GetHotelResponse createHotel(CreateHotel hotel) {
    if (repository.existsById(hotel.getId())) {
      throw new AlreadyExistsException("Hotel", hotel.getId());
    }

    var hotelEntity = mapper.mapToHotelEntity(hotel);
    return mapper.mapToGetHotelResponse(repository.save(hotelEntity));
  }

  @Override
  public void deleteHotel(Long id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Hotel", id);
    }

    repository.deleteById(id);
  }

  @Override
  public GetHotelResponse addAmenitiesToHotel(Long hotelId, Amenities amenities) {
    var hotel = getExistingHotel(hotelId);
    var hotelAmenities = new ArrayList<>(hotel.getAmenities());
    var oldIds = amenityMapper.getAmenitiesIds(hotelAmenities);

    var newAmenities = amenityMapper.mapToAmenitiesList(amenities);

    // TODO: Refactor Hotel to use Set and avoid these uniqueness checks
    newAmenities.forEach(amenity -> {
      if (!oldIds.contains(amenity.getId())) {
        hotelAmenities.add(amenity);
        oldIds.add(amenity.getId());
      }
    });
    hotel.setAmenities(hotelAmenities);

    return mapper.mapToGetHotelResponse(repository.save(hotel));
  }

  @Override
  public GetHotelResponse removeAmenitiesFromHotel(Long hotelId, Amenities amenities) {
    var hotel = getExistingHotel(hotelId);

    var amenitiesToRemove = amenityMapper.mapToAmenitiesList(amenities);
    var idsToRemove = amenityMapper.getAmenitiesIds(amenitiesToRemove);

    var remainingAmenities = hotel.getAmenities().stream()
        .filter(toRemove -> !idsToRemove.contains(toRemove.getId()))
        .toList();
    hotel.setAmenities(remainingAmenities);

    return mapper.mapToGetHotelResponse(repository.save(hotel));
  }

  private Hotel getExistingHotel(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Hotel", id));
  }
}
