package com.example.propertyview.dto;

/**
 * DTO для ответа GET /hotels (краткая информация).
 * DTO — это "что мы отдаём наружу", отдельно от сущностей БД.
 */
public record HotelShortResponse(
        long id
) {}
