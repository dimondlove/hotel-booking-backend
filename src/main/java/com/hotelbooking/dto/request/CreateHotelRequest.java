package com.hotelbooking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateHotelRequest {
    @NotBlank(message = "Hotel name is required")
    private String name;

    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    private String phone;
    private String email;
    private List<String> amenities;
    private List<String> images;
}
