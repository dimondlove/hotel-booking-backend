package com.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String address;

    private String phone;

    private String email;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "hotel_images", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    private Double rating;
}
