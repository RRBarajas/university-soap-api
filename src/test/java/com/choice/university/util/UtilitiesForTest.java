package com.choice.university.util;

import com.choice.university.service.model.Address;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.Amenity;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.GetAmenitiesResponse;
import com.choice.university.service.model.GetAmenityResponse;
import com.choice.university.service.model.GetHotelResponse;
import com.choice.university.service.model.GetHotelsResponse;
import com.choice.university.service.model.Hotel;
import com.choice.university.service.model.Hotels;
import com.choice.university.service.model.ObjectFactory;
import java.util.List;

/**
 * Utility methods used for Unit tests.
 */
public final class UtilitiesForTest {

  private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

  private UtilitiesForTest() {
  }

  // Amenities related mocks
  public static com.choice.university.entity.Amenity getAmenityEntity() {
    var amenityEntity = new com.choice.university.entity.Amenity();
    amenityEntity.setId(10L);
    amenityEntity.setName("Amenity name");
    return amenityEntity;
  }

  public static Amenity getAmenity() {
    var amenity = OBJECT_FACTORY.createAmenity();
    amenity.setId(10L);
    amenity.setName("Amenity name");
    return amenity;
  }

  public static List<Amenity> getAmenityList() {
    return List.of(getAmenity());
  }

  public static List<Amenity> getAmenityListWithDuplicates() {
    return List.of(getAmenity(), getAmenity());
  }

  public static Amenities getAmenities() {
    var amenities = OBJECT_FACTORY.createAmenities();
    amenities.getAmenity().addAll(getAmenityList());
    return amenities;
  }

  public static Amenities getAmenitiesWithDuplicates() {
    var amenities = OBJECT_FACTORY.createAmenities();
    amenities.getAmenity().addAll(getAmenityListWithDuplicates());
    return amenities;
  }

  public static GetAmenityResponse getAmenityResponse() {
    var response = OBJECT_FACTORY.createGetAmenityResponse();
    response.setAmenity(getAmenity());
    return response;
  }

  public static GetAmenitiesResponse getAmenitiesResponse() {
    var response = OBJECT_FACTORY.createGetAmenitiesResponse();
    response.setAmenities(getAmenities());
    return response;
  }

  //  Hotel related mocks
  public static com.choice.university.entity.Hotel getHotelEntity() {
    var hotel = new com.choice.university.entity.Hotel();
    hotel.setId(1L);
    hotel.setName("Hotel name");
    hotel.setRating(5);
    hotel.setAddress(getAddressEntity());
    hotel.setAmenities(List.of(getAmenityEntity()));
    return hotel;
  }

  public static Hotel getHotel() {
    var hotel = OBJECT_FACTORY.createHotel();
    hotel.setId(1L);
    hotel.setName("Hotel name");
    hotel.setRating(5);
    hotel.setAddress(getAddress());
    hotel.setAmenities(getAmenities());
    return hotel;
  }

  public static Hotels getHotels() {
    var hotels = OBJECT_FACTORY.createHotels();
    hotels.getHotel().add(getHotel());
    return hotels;
  }

  public static CreateHotel getCreateHotel() {
    var hotel = OBJECT_FACTORY.createCreateHotel();
    hotel.setId(1L);
    hotel.setName("Hotel name");
    hotel.setRating(5);
    hotel.setAddress(getAddress());
    hotel.setAmenities(getAmenities());
    return hotel;
  }

  public static com.choice.university.entity.Address getAddressEntity() {
    var address = new com.choice.university.entity.Address();
    address.setId(100L);
    address.setStreetName("Street name");
    address.setStateName("State name");
    address.setCountryName("Country name");
    return address;
  }

  public static Address getAddress() {
    var address = OBJECT_FACTORY.createAddress();
    address.setId(100L);
    address.setStreetName("Street name");
    address.setStateName("State name");
    address.setCountryName("Country name");
    return address;
  }

  public static GetHotelResponse getHotelResponse() {
    var response = OBJECT_FACTORY.createGetHotelResponse();
    response.setHotel(getHotel());
    return response;
  }

  public static GetHotelsResponse getHotelsResponse() {
    var response = OBJECT_FACTORY.createGetHotelsResponse();
    response.setHotels(getHotels());
    return response;
  }
}
