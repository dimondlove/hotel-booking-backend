package com.hotelbooking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateHotelRequest {
    private String name;
    private String description;
    private String address;
    private String city;
    private String phone;
    private String email;
    private List<String> amenities;
    private List<String> images;
    private Boolean active;
}
