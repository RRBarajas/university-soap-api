package com.choice.university.endpoint;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;

import com.choice.university.service.AmenityService;
import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityRequest;
import com.choice.university.service.model.GetAmenityResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AmenityEndpoint {

  private final AmenityService service;

  public AmenityEndpoint(AmenityService service) {
    this.service = service;
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "getAmenityRequest")
  @ResponsePayload
  public GetAmenityResponse getAmenity(@RequestPayload GetAmenityRequest request) {
    return service.getAmenity(request.getId());
  }

  @PayloadRoot(namespace = UNIVERSITY_NAMESPACE_URI, localPart = "getAmenitiesRequest")
  @ResponsePayload
  public GetAmenitiesResponse getAllAmenities() {
    return service.getAllAmenities();
  }
}