package com.choice.university.endpoint;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;
import static com.choice.university.util.UtilitiesForTest.getHotelResponse;
import static com.choice.university.util.UtilitiesForTest.getHotelsResponse;
import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import com.choice.university.service.HotelService;
import com.choice.university.service.model.GetHotelResponse;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@WebServiceServerTest(endpoints = HotelEndpoint.class)
class HotelEndpointTest {


  private static final Map<String, String> NAMESPACE_MAPPING = Map.of(
      "usi", UNIVERSITY_NAMESPACE_URI);

  @Autowired
  private MockWebServiceClient client;

  @MockBean
  private HotelService service;

  @Test
  void shouldReturnValidXMLForGetHotelResponse_whenCallingGetHotelByIdWithValidId() {
    var hotelResponse = getHotelResponse();
    when(service.getHotel(anyLong())).thenReturn(hotelResponse);

    var request = new StringSource("""
        <usi:getHotelById xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:id>1</usi:id>
        </usi:getHotelById>
        """);

    runAndAssertRequestForGetHotelResponse(request, hotelResponse);
  }

  @Test
  void shouldReturnValidXMLGetAmenitiesResponse_whenCallingGetAllAmenities() {
    var hotelsResponse = getHotelsResponse();
    var firstHotel = hotelsResponse.getHotels().getHotel().get(0);
    when(service.getHotelsByName(any(), anyInt(), anyInt())).thenReturn(hotelsResponse);

    var request = new StringSource("""
        <usi:getHotelsByName xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:name>Name</usi:name>
        </usi:getHotelsByName>
        """);

    var hotelsXpath = "usi:getHotelsResponse/usi:hotels";
    var firstHotelXpath = format("%s[1]/usi:hotel", hotelsXpath);
    client.sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(xpath("usi:getHotelsResponse/usi:count", NAMESPACE_MAPPING)
            .evaluatesTo(hotelsResponse.getHotels().getHotel().size()))
        .andExpect(xpath(hotelsXpath, NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(String.format("%s[1]", hotelsXpath), NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:id", firstHotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(firstHotel.getId()))
        .andExpect(xpath(format("%s/usi:name", firstHotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(firstHotel.getName()))
        .andExpect(xpath(format("%s/usi:rating", firstHotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(firstHotel.getRating()))
        .andExpect(xpath(format("%s/usi:address", firstHotelXpath), NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:amenities", firstHotelXpath), NAMESPACE_MAPPING)
            .exists());
  }

  @Test
  void shouldReturnValidXMLForGetHotelResponse_whenCallingCreateHotelWithValidData() {
    var hotelResponse = getHotelResponse();
    when(service.createHotel(any())).thenReturn(hotelResponse);

    var request = new StringSource("""
        <usi:createHotel xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:id>1</usi:id>
          <usi:name>Name</usi:name>
          <usi:rating>5</usi:rating>
        </usi:createHotel>
        """);

    runAndAssertRequestForGetHotelResponse(request, hotelResponse);
  }

  @Test
  void shouldNotReturnAnything_whenCallingDeleteHotelWithValidId() {
    var request = new StringSource("""
        <usi:deleteHotel xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:id>1</usi:id>
        </usi:deleteHotel>
        """);

    client.sendRequest(withPayload(request))
        .andExpect(noFault());
  }

  @Test
  void shouldReturnValidXMLForGetHotelResponse_whenCallingAddHotelAmenitiesWithValidAmenities() {
    var hotelResponse = getHotelResponse();
    when(service.addAmenitiesToHotel(anyLong(), any())).thenReturn(hotelResponse);

    var request = new StringSource("""
        <usi:addHotelAmenities xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:hotelId>1</usi:hotelId>
          <usi:amenities>
              <usi:amenity>
                  <usi:id>1</usi:id>
              </usi:amenity>
          </usi:amenities>
        </usi:addHotelAmenities>
        """);

    runAndAssertRequestForGetHotelResponse(request, hotelResponse);
  }

  @Test
  void shouldReturnValidXMLForGetHotelResponse_whenCallingRemoveHotelAmenitiesWithValidAmenities() {
    var hotelResponse = getHotelResponse();
    when(service.removeAmenitiesFromHotel(anyLong(), any())).thenReturn(hotelResponse);

    var request = new StringSource("""
        <usi:removeHotelAmenities xmlns:usi="http://www.encora.com/choice/university-soap-api">
          <usi:hotelId>1</usi:hotelId>
          <usi:amenities>
              <usi:amenity>
                  <usi:id>1</usi:id>
              </usi:amenity>
          </usi:amenities>
        </usi:removeHotelAmenities>
        """);

    runAndAssertRequestForGetHotelResponse(request, hotelResponse);
  }

  private void runAndAssertRequestForGetHotelResponse(StringSource request,
      GetHotelResponse expectedResponse) {
    var hotelXpath = "usi:getHotelResponse/usi:hotel";
    client.sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(xpath(hotelXpath, NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:id", hotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(expectedResponse.getHotel().getId()))
        .andExpect(xpath(format("%s/usi:name", hotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(expectedResponse.getHotel().getName()))
        .andExpect(xpath(format("%s/usi:rating", hotelXpath), NAMESPACE_MAPPING)
            .evaluatesTo(expectedResponse.getHotel().getRating()))
        .andExpect(xpath(format("%s/usi:address", hotelXpath), NAMESPACE_MAPPING)
            .exists())
        .andExpect(xpath(format("%s/usi:amenities", hotelXpath), NAMESPACE_MAPPING)
            .exists());
  }
}