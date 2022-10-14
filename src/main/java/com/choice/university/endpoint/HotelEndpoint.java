package com.choice.university.endpoint;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;

import com.choice.university.service.HotelService;
import com.choice.university.service.model.GetHotelById;
import com.choice.university.service.model.GetHotelResponse;
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
  public GetHotelResponse getHotel(@RequestPayload GetHotelById request) {
    return service.getHotel(request.getId());
  }
}