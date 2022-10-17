package com.choice.university.service;

import com.choice.university.entity.Hotel;
import com.choice.university.repository.HotelRepository;
import com.choice.university.service.exception.AlreadyExistsException;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.AmenityMapper;
import com.choice.university.service.mapper.HotelMapper;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

  private final HotelRepository repository;
  private final HotelMapper mapper;
  private final AmenityMapper amenityMapper;

  public HotelService(HotelRepository repository,
      HotelMapper mapper,
      AmenityMapper amenityMapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.amenityMapper = amenityMapper;
  }

  public GetHotelResponse getHotel(Long id) {
    var hotel = getExistingHotel(id);

    return mapper.mapToGetHotelResponse(hotel);
  }

  public GetHotelsResponse getHotelsByName(String name) {
    var hotels = repository.findAllByNameContainingIgnoreCase(name);

    return mapper.mapToGetHotelsResponse(hotels);
  }

  public GetHotelResponse createHotel(CreateHotel hotel) {
    if (repository.existsById(hotel.getId())) {
      throw new AlreadyExistsException("Hotel", hotel.getId());
    }

    var hotelEntity = mapper.mapToHotelEntity(hotel);
    return mapper.mapToGetHotelResponse(repository.save(hotelEntity));
  }

  public void deleteHotel(Long id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Hotel", id);
    }

    repository.deleteById(id);
  }

  public GetHotelResponse addAmenitiesToHotel(Long hotelId, Amenities amenities) {
    var hotel = getExistingHotel(hotelId);
    var oldIds = amenityMapper.getAmenitiesIds(hotel.getAmenities());

    var newAmenities = amenityMapper.mapToAmenitiesList(amenities);

    // TODO: Refactor Hotel to use Set and avoid these uniqueness checks
    newAmenities.forEach(amenity -> {
      if (!oldIds.contains(amenity.getId())) {
        hotel.getAmenities().add(amenity);
        oldIds.add(amenity.getId());
      }
    });

    return mapper.mapToGetHotelResponse(repository.save(hotel));
  }

  public GetHotelResponse removeAmenitiesToHotel(Long hotelId, Amenities amenities) {
    var hotel = getExistingHotel(hotelId);

    var amenitiesToRemove = amenityMapper.mapToAmenitiesList(amenities);
    var idsToRemove = amenityMapper.getAmenitiesIds(amenitiesToRemove);

    hotel.getAmenities().removeIf(amenity -> idsToRemove.contains(amenity.getId()));

    return mapper.mapToGetHotelResponse(repository.save(hotel));
  }

  private Hotel getExistingHotel(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Hotel", id));
  }
}
