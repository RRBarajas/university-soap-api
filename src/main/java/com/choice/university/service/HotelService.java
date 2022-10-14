package com.choice.university.service;

import com.choice.university.repository.HotelRepository;
import com.choice.university.service.exception.AlreadyExistsException;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.HotelMapper;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

  private final HotelRepository repository;
  private final HotelMapper mapper;

  public HotelService(HotelRepository repository,
      HotelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public GetHotelResponse getHotel(Long id) {
    var hotel = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Hotel", id));

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

    // TODO: If passing invalid Amenities, the response contains NIL nodes
    return mapper.mapToGetHotelResponse(repository.save(hotelEntity));
  }

  public void deleteHotel(Long id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Hotel", id);
    }
    repository.deleteById(id);
  }
}
