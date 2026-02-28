package com.example.propertyview.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO запроса для POST /property-view/hotels.
 * Это "форма", в которой клиент присылает данные для создания отеля в БД.
 */
public record CreateHotelRequest(

        @NotBlank String name,

        // optional по ТЗ
        String description,

        @NotBlank String brand,

        @NotNull @Valid Address address,

        @NotNull @Valid Contacts contacts,

        @NotNull @Valid ArrivalTime arrivalTime,

        // Можно прислать пустой список, но элементы не должны быть пустыми строками
        List<@NotBlank String> amenities
) {

    /**
     * Вложенный DTO "адрес" (как в ТЗ).
     */
    public record Address(
            @NotNull Integer houseNumber,
            @NotBlank String street,
            @NotBlank String city,
            @NotBlank String country,
            @NotBlank String postCode
    ) {}

    /**
     * Вложенный DTO "контакты" (как в ТЗ).
     */
    public record Contacts(
            @NotBlank String phone,
            @NotBlank @Email String email
    ) {}

    /**
     * Вложенный DTO "время заезда/выезда" (как в ТЗ).
     * checkOut — optional.
     */
    public record ArrivalTime(
            @NotBlank String checkIn,
            String checkOut
    ) {}
}
