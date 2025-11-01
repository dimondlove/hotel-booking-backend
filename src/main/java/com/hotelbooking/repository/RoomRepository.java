package com.hotelbooking.repository;

import com.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelIdAndAvailableTrue(Long hotelId);
    List<Room> findByAvailableTrue();

    @Query("SELECT r FROM Room r WHERE r.hotel.city = :city AND r.available = true")
    List<Room> findAvailableRoomsByCity(@Param("city") String city);
}
