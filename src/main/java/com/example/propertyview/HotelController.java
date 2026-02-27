package com.example.propertyview;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер — это "входная дверь" в наше приложение по HTTP.
 * Здесь будут все эндпоинты (URL), которые требует ТЗ.
 */
@RestController
@RequestMapping("/property-view") // общий префикс для всех методов API по ТЗ
public class HotelController {

    /**
     * Пока возвращаем пустой список.
     * На следующем шаге подключим сервис и БД и начнём отдавать реальные отели.
     */
    @GetMapping("/hotels")
    public List<Object> getHotels() {
        return List.of();
    }
}