package com.choice.university.endpoint;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;
import static com.choice.university.util.UtilitiesForTest.getAmenitiesResponse;
import static com.choice.university.util.UtilitiesForTest.getAmenityResponse;
import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import com.choice.university.service.AmenityService;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@WebServiceServerTest(endpoints = AmenityEndpoint.class)
class AmenityEndpointTest {


  private static final Map<String, String> NAMESPACE_MAPPING = Map.of(
      "usi", UNIVERSITY_NAMESPACE_URI);

  @Autowired
  private MockWebServiceClient client;

  @MockBean
  private AmenityService service;

  @Test
  void shouldReturnValidXMLForGetAmenityResponse_whenCallingGetAmenityWithValidId() {
    var amenityResponse = getAmenityResponse();
    when(service.getAmenity(anyLong())).thenReturn(amenityResponse);

    var request = new StringSource("""
        <usi:getAmenityRequest xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:id>1</usi:id>
        </usi:getAmenityRequest>
        """);

    var amenityXpath = "usi:getAmenityResponse/usi:amenity";
    client.sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(xpath(amenityXpath, NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:id", amenityXpath), NAMESPACE_MAPPING)
            .evaluatesTo(amenityResponse.getAmenity().getId()))
        .andExpect(xpath(format("%s/usi:name", amenityXpath), NAMESPACE_MAPPING)
            .evaluatesTo(amenityResponse.getAmenity().getName()));
  }

  @Test
  void shouldReturnValidXMLGetAmenitiesResponse_whenCallingGetAllAmenities() {
    var amenitiesResponse = getAmenitiesResponse();
    var firstAmenity = amenitiesResponse.getAmenities().getAmenity().get(0);
    when(service.getAllAmenities()).thenReturn(amenitiesResponse);

    var request = new StringSource("""
        <usi:getAmenitiesResponse xmlns:usi="http://www.encora.com/choice/university-soap-api" />
        """);

    var amenitiesXpath = "usi:getAmenitiesResponse/usi:amenities";
    var firstAmenityXpath = format("%s[1]/usi:amenity", amenitiesXpath);
    client.sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(xpath(amenitiesXpath, NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s[1]", amenitiesXpath), NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:id", firstAmenityXpath), NAMESPACE_MAPPING)
            .evaluatesTo(firstAmenity.getId()))
        .andExpect(xpath(format("%s/usi:name", firstAmenityXpath), NAMESPACE_MAPPING)
            .evaluatesTo(firstAmenity.getName()));
  }
}