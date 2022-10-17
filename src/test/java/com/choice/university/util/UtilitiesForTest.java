package com.choice.university.util;

import com.choice.university.service.model.Address;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.Amenity;
import com.choice.university.service.model.Hotel;
import java.util.List;

/**
 * Utility methods used for Unit tests.
 */
public final class UtilitiesForTest {

  private UtilitiesForTest() {
  }

  public static Amenity getAmenity() {
    var amenity = new Amenity();
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
    var amenities = new Amenities();
    amenities.getAmenity().addAll(getAmenityList());
    return amenities;
  }

  public static Hotel getHotel() {
    var hotel = new Hotel();
    hotel.setId(1L);
    hotel.setName("Hotel name");
    hotel.setRating(5);
    hotel.setAddress(getAddress());
    hotel.setAmenities(getAmenities());
    return hotel;
  }

  public static Address getAddress() {
    var address = new Address();
    address.setId(100L);
    address.setStreetName("Street name");
    address.setStateName("State name");
    address.setCountryName("Country name");
    return address;
  }
}
