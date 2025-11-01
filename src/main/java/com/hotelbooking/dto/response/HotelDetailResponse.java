package com.hotelbooking.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class HotelDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String phone;
    private String email;
    private List<String> amenities;
    private List<String> images;
    private Double rating;
    private Integer reviewCount;
    private List<RoomResponse> rooms;
}
