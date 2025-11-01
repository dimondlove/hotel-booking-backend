package com.hotelbooking.dto.response;

import com.hotelbooking.model.Room;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomResponse {
    private Long id;
    private String name;
    private String description;
    private Room.RoomType roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private List<String> amenities;
    private List<String> images;
    private Boolean available;
    private Long hotelId;
    private String hotelName;
}
