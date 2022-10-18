package com.choice.university.util;

import com.choice.university.service.model.Address;
import com.choice.university.service.model.Amenities;
import com.choice.university.service.model.Amenity;
import com.choice.university.service.model.CreateHotel;
import com.choice.university.service.model.Hotel;
import java.util.List;

/**
 * Utility methods used for Unit tests.
 */
public final class UtilitiesForTest {

  private UtilitiesForTest() {
  }

  public static com.choice.university.entity.Amenity getAmenityEntity() {
    var amenityEntity = new com.choice.university.entity.Amenity();
    amenityEntity.setId(10L);
    amenityEntity.setName("Amenity name");
    return amenityEntity;
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

  public static Amenities getAmenitiesWithDuplicates() {
    var amenities = new Amenities();
    amenities.getAmenity().addAll(getAmenityListWithDuplicates());
    return amenities;
  }

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
    var hotel = new Hotel();
    hotel.setId(1L);
    hotel.setName("Hotel name");
    hotel.setRating(5);
    hotel.setAddress(getAddress());
    hotel.setAmenities(getAmenities());
    return hotel;
  }

  public static CreateHotel getCreateHotel() {
    var hotel = new CreateHotel();
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
    var address = new Address();
    address.setId(100L);
    address.setStreetName("Street name");
    address.setStateName("State name");
    address.setCountryName("Country name");
    return address;
  }
}
