package com.hotelbooking.controller;

import com.hotelbooking.dto.request.CreateRoomRequest;
import com.hotelbooking.dto.response.RoomResponse;
import com.hotelbooking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotelId(@PathVariable Long hotelId) {
        List<RoomResponse> rooms = roomService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<RoomResponse>> getAvailableRoomsByCity(@PathVariable String city) {
        List<RoomResponse> rooms = roomService.getAvailableRoomsByCity(city);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        RoomResponse room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        RoomResponse room = roomService.createRoom(request);
        return ResponseEntity.ok(room);
    }

    @PatchMapping("/{id}/availability")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> updateRoomAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        RoomResponse room = roomService.updateRoomAvailability(id, available);
        return ResponseEntity.ok(room);
    }
}