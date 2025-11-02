package com.hotelbooking.repository;

import com.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findByUserIdAndStatusInOrderByCheckInDateAsc(Long userId, List<Booking.BookingStatus> statuses);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.status IN ('CONFIRMED', 'PENDING') " +
            "AND ((b.checkInDate BETWEEN :checkIn AND :checkOut) OR " +
            "(b.checkOutDate BETWEEN :checkIn AND :checkOut) OR " +
            "(b.checkInDate <= :checkIn AND b.checkOutDate >= :checkOut))")
    List<Booking> findConflictingBookings(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
            );

    @Query("SELECT b FROM Booking b WHERE b.room.hotel.id = :hotelId ORDER BY b.createdAt DESC")
    List<Booking> findByHotelId(@Param("hotelId") Long hotelId);

    List<Booking> findByStatusOrderByCreatedAtDesc(Booking.BookingStatus status);
}
