package com.hotelbooking.controller;

import com.hotelbooking.dto.request.CreateBookingRequest;
import com.hotelbooking.dto.response.ApiResponse;
import com.hotelbooking.dto.response.BookingResponse;
import com.hotelbooking.model.Booking;
import com.hotelbooking.model.User;
import com.hotelbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getUserBookings(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<BookingResponse> bookings = bookingService.getUserBookings(user.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/my/active")
    public ResponseEntity<List<BookingResponse>> getUserActiveBookings(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<BookingResponse> bookings = bookingService.getUserActiveBookings(user.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BookingResponse booking = bookingService.getBookingById(id, user);
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(
            @Valid @RequestBody CreateBookingRequest request,
            Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            BookingResponse booking = bookingService.createBooking(request, user);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            BookingResponse booking = bookingService.cancelBooking(id, user);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
        }
    }

    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getHotelBookings(@PathVariable Long hotelId) {
        List<BookingResponse> bookings = bookingService.getHotelBookings(hotelId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getBookingsByStatus(@PathVariable Booking.BookingStatus status) {
        List<BookingResponse> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam Booking.BookingStatus status,
            Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            BookingResponse booking = bookingService.updateBookingStatus(id, status, user);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
        }
    }
}
