package com.hotelbooking.dto.response;

import com.hotelbooking.model.Booking;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long id;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private Long roomId;
    private String roomName;
    private Long hotelId;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;

    private BigDecimal totalPrice;
    private Booking.BookingStatus status;
    private String specialRequests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.userId = booking.getUser().getId();
        this.userFirstName = booking.getUser().getFirstName();
        this.userLastName = booking.getUser().getLastName();
        this.roomId = booking.getRoom().getId();
        this.roomName = booking.getRoom().getName();
        this.hotelId = booking.getRoom().getHotel().getId();
        this.hotelName = booking.getRoom().getHotel().getName();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.guests = booking.getGuests();
        this.totalPrice = booking.getTotalPrice();
        this.status = booking.getStatus();
        this.specialRequests = booking.getSpecialRequests();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();
    }
}
