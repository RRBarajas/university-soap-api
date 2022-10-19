package com.choice.university.endpoint;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;

import com.choice.university.service.HotelService;
import com.choice.university.service.model.AddHotelAmenities;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.DeleteHotel;
import com.choice.university.service.model.GetHotelById;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsByName;
import com.choice.university.service.model.GetHotelsResponse;
import com.choice.university.service.model.RemoveHotelAmenities;
import java.util.Optional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {

  private final HotelService service;

  public HotelEndpoint(HotelService service) {
    this.service = service;
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "getHotelById")
  @ResponsePayload
  public GetHotelResponse getHotelById(@RequestPayload GetHotelById request) {
    return service.getHotel(request.getId());
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "getHotelsByName")
  @ResponsePayload
  public GetHotelsResponse getHotelsByName(@RequestPayload GetHotelsByName request) {
    return service.getHotelsByName(request.getName(),
        Optional.ofNullable(request.getPageNumber()).orElse(0),
        Optional.ofNullable(request.getPageSize()).orElse(1));
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "createHotel")
  @ResponsePayload
  public GetHotelResponse createHotel(@RequestPayload CreateHotel request) {
    return service.createHotel(request);
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "deleteHotel")
  @ResponsePayload
  public void deleteHotel(@RequestPayload DeleteHotel request) {
    service.deleteHotel(request.getId());
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "addHotelAmenities")
  @ResponsePayload
  public GetHotelResponse addHotelAmenities(@RequestPayload AddHotelAmenities request) {
    return service.addAmenitiesToHotel(request.getHotelId(), request.getAmenities());
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "removeHotelAmenities")
  @ResponsePayload
  public GetHotelResponse removeHotelAmenities(@RequestPayload RemoveHotelAmenities request) {
    return service.removeAmenitiesFromHotel(request.getHotelId(), request.getAmenities());
  }
}