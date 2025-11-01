package com.hotelbooking.repository;

import com.hotelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByActiveTrue();
    List<Hotel> findByCityAndActiveTrue(String city);

    @Query("SELECT h FROM Hotel h WHERE h.active = true AND LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<Hotel> searchByCity(@Param("city") String city);
}
