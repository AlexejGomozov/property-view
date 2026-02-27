package com.example.propertyview;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер — это "входная дверь" в наше приложение по HTTP.
 */
@RestController
@RequestMapping("/property-view") // общий префикс для всех методов API по ТЗ
public class HotelController {

    private final HotelRepository hotelRepository;

    /**
     * Внедряем репозиторий (доступ к таблице hotels).
     * Spring сам создаст реализацию HotelRepository и передаст сюда.
     */
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Пока возвращаем список отелей из БД.
     * Так как мы ещё не добавили данные — будет пустой список [].
     */
        @GetMapping("/hotels")
    public List<com.example.propertyview.dto.HotelShortResponse> getHotels() {
        return hotelRepository.findAll().stream()
                .map(h -> new com.example.propertyview.dto.HotelShortResponse(h.getId()))
                .toList();
    }
}