package com.choice.university.service;

import com.choice.university.repository.HotelRepository;
import com.choice.university.service.exception.EntityNotFoundException;
import com.choice.university.service.mapper.HotelMapper;
import com.choice.university.service.model.GetHotelResponse;
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
}
