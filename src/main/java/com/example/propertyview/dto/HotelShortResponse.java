package com.example.propertyview.dto;

/**
 * DTO для ответа GET /hotels (краткая информация).
 * Важно: address здесь строкой (по ТЗ).
 */
public record HotelShortResponse(
        long id,
        String name,
        String description,
        String address,
        String phone
) {}


