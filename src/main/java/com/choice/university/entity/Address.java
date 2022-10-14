package com.choice.university.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String streetName;

  private String stateName;

  private String countryName;
}
