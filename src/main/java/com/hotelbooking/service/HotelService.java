package com.hotelbooking.service;

import com.hotelbooking.dto.request.CreateHotelRequest;
import com.hotelbooking.dto.request.UpdateHotelRequest;
import com.hotelbooking.dto.response.HotelDetailResponse;
import com.hotelbooking.dto.response.HotelResponse;
import com.hotelbooking.dto.response.RoomResponse;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Room;
import com.hotelbooking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findByActiveTrue().stream()
                .map(this::convertToHotelResponse)
                .collect(Collectors.toList());
    }

    public List<HotelResponse> getHotelsByCity (String city) {
        return hotelRepository.findByCityAndActiveTrue(city).stream()
                .map(this::convertToHotelResponse)
                .collect(Collectors.toList());
    }

    public HotelDetailResponse getHotelById (Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        return convertToHotelDetailResponse(hotel);
    }

    @Transactional
    public HotelResponse createHotel(CreateHotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setPhone(request.getPhone());
        hotel.setEmail(request.getEmail());
        hotel.setAmenities(request.getAmenities());
        hotel.setImages(request.getImages());

        Hotel savedHotel = hotelRepository.save(hotel);
        return convertToHotelResponse(savedHotel);
    }

    @Transactional
    public HotelResponse updateHotel(Long id, UpdateHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        if (request.getName() != null) hotel.setName(request.getName());
        if (request.getDescription() != null) hotel.setDescription(request.getDescription());
        if (request.getAddress() != null) hotel.setAddress(request.getAddress());
        if (request.getCity() != null) hotel.setCity(request.getCity());
        if (request.getPhone() != null) hotel.setPhone(request.getPhone());
        if (request.getEmail() != null) hotel.setEmail(request.getEmail());
        if (request.getAmenities() != null) hotel.setAmenities(request.getAmenities());
        if (request.getImages() != null) hotel.setImages(request.getImages());
        if (request.getActive() != null) hotel.setActive(request.getActive());

        Hotel updatedHotel = hotelRepository.save(hotel);
        return convertToHotelResponse(updatedHotel);
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotel.setActive(false);
        hotelRepository.save(hotel);
    }

    private HotelResponse convertToHotelResponse(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setDescription(hotel.getDescription());
        response.setAddress(hotel.getAddress());
        response.setCity(hotel.getCity());
        response.setPhone(hotel.getPhone());
        response.setEmail(hotel.getEmail());
        response.setAmenities(hotel.getAmenities());
        response.setImages(hotel.getImages());
        response.setRating(hotel.getRating());
        response.setReviewCount(hotel.getReviewCount());
        return response;
    }

    private HotelDetailResponse convertToHotelDetailResponse(Hotel hotel) {
        HotelDetailResponse response = new HotelDetailResponse();
        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setDescription(hotel.getDescription());
        response.setAddress(hotel.getAddress());
        response.setCity(hotel.getCity());
        response.setPhone(hotel.getPhone());
        response.setEmail(hotel.getEmail());
        response.setAmenities(hotel.getAmenities());
        response.setImages(hotel.getImages());
        response.setRating(hotel.getRating());
        response.setReviewCount(hotel.getReviewCount());

        List<RoomResponse> roomResponses = hotel.getRooms().stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
        response.setRooms(roomResponses);

        return response;
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
}
