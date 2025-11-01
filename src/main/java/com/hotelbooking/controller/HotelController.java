package com.hotelbooking.controller;

import com.hotelbooking.dto.request.CreateHotelRequest;
import com.hotelbooking.dto.request.UpdateHotelRequest;
import com.hotelbooking.dto.response.HotelDetailResponse;
import com.hotelbooking.dto.response.HotelResponse;
import com.hotelbooking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCity(@PathVariable String city) {
        List<HotelResponse> hotels = hotelService.getHotelsByCity(city);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailResponse> getHotelById (@PathVariable Long id) {
        HotelDetailResponse hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody CreateHotelRequest request) {
        HotelResponse hotel = hotelService.createHotel(request);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> updateHotel(
            @PathVariable Long id, @Valid @RequestBody UpdateHotelRequest request) {
        HotelResponse hotel = hotelService.updateHotel(id, request);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok().build();
    }
}
