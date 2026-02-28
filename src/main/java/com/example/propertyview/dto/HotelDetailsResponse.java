package com.example.propertyview.dto;

import java.util.List;

/**
 * DTO для ответа GET /hotels/{id} (подробная информация).
 */
public record HotelDetailsResponse(
        long id,
        String name,
        String description,
        String brand,
        Address address,
        Contacts contacts,
        ArrivalTime arrivalTime,
        List<String> amenities
) {
    public record Address(
            int houseNumber,
            String street,
            String city,
            String country,
            String postCode
    ) {}

    public record Contacts(
            String phone,
            String email
    ) {}

    public record ArrivalTime(
            String checkIn,
            String checkOut
    ) {}
}