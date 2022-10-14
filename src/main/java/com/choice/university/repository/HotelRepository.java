package com.choice.university.repository;

import com.choice.university.entity.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

  List<Hotel> findAllByNameContainingIgnoreCase(String email);
}
