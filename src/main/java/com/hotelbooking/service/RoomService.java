package com.hotelbooking.service;

import com.hotelbooking.dto.request.CreateRoomRequest;
import com.hotelbooking.dto.request.UpdateRoomRequest;
import com.hotelbooking.dto.response.RoomResponse;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Room;
import com.hotelbooking.repository.HotelRepository;
import com.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findByAvailableTrue().stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getAllRoomsForAdmin() {
        return roomRepository.findAll().stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelIdAndAvailableTrue(hotelId).stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getRoomsByHotelIdForAdmin(Long hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getAvailableRoomsByCity(String city) {
        return roomRepository.findAvailableRoomsByCity(city).stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return convertToRoomResponse(room);
    }

    @Transactional
    public RoomResponse createRoom(CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + request.getHotelId()));

        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        room.setAmenities(request.getAmenities());
        room.setImages(request.getImages());
        room.setHotel(hotel);

        Room savedRoom = roomRepository.save(room);
        return convertToRoomResponse(savedRoom);
    }

    @Transactional
    public RoomResponse updateRoomAvailability(Long id, Boolean available) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        room.setAvailable(available);
        Room updatedRoom = roomRepository.save(room);
        return convertToRoomResponse(updatedRoom);
    }

    private RoomResponse convertToRoomResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setName(room.getName());
        response.setDescription(room.getDescription());
        response.setRoomType(room.getRoomType());
        response.setCapacity(room.getCapacity());
        response.setPricePerNight(room.getPricePerNight());
        response.setAmenities(room.getAmenities());
        response.setImages(room.getImages());
        response.setAvailable(room.getAvailable());
        response.setHotelId(room.getHotel().getId());
        response.setHotelName(room.getHotel().getName());
        return response;
    }

    @Transactional
    public RoomResponse updateRoom(Long id, UpdateRoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        room.setAmenities(request.getAmenities());
        room.setImages(request.getImages());
        room.setAvailable(request.getAvailable());

        Room updatedRoom = roomRepository.save(room);
        return convertToRoomResponse(updatedRoom);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        roomRepository.delete(room);
    }
}
