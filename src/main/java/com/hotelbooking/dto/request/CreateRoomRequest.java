package com.hotelbooking.dto.request;

import com.hotelbooking.model.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateRoomRequest {
    @NotBlank(message = "Room name is required")
    private String name;

    private String description;

    @NotNull(message = "Room type is required")
    private Room.RoomType roomType;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal pricePerNight;

    private List<String> amenities;
    private List<String> images;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;
}
