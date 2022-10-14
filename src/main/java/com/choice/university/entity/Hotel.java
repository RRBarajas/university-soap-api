package com.choice.university.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.NotFoundAction.IGNORE;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

@Entity
@Getter
@Setter
public class Hotel {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String name;

  private Integer rating;

  @ManyToOne(fetch = EAGER, cascade = ALL)
  @JoinColumn(name = "address_id")
  private Address address;

  // TODO: When upserting, non-existent IDs are returned as NULL (and NIL XML nodes)
  @ManyToMany(cascade = PERSIST)
  @NotFound(action = IGNORE)
  private List<Amenity> amenities;
}
