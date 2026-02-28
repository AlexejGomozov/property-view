package com.example.propertyview;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.propertyview.dto.CreateHotelRequest;
import com.example.propertyview.dto.HotelShortResponse;

/**
 * Контроллер — это "входная дверь" в наше приложение по HTTP.
 */
@RestController
@RequestMapping("/property-view") // общий префикс для всех методов API по ТЗ
public class HotelController {

    private final HotelService hotelService;
    private final HotelRepository hotelRepository;

    public HotelController(HotelService hotelService, HotelRepository hotelRepository) {
        this.hotelService = hotelService;
        this.hotelRepository = hotelRepository;
    }

    /**
     * GET /property-view/hotels — краткий список отелей (DTO).
     */
    @GetMapping("/hotels")
    public List<HotelShortResponse> getHotels() {
        return hotelRepository.findAll().stream()
                .map(h -> new HotelShortResponse(
                        h.getId(),
                        h.getName(),
                        h.getDescription(),
                        formatAddress(h.getAddress()),
                        h.getContacts().getPhone()
                ))
                .toList();
    }

    /**
     * GET /property-view/hotels/{id} — подробная информация об одном отеле.
     */
    @GetMapping("/hotels/{id}")
    public com.example.propertyview.dto.HotelDetailsResponse getHotelById(@PathVariable long id) {
        return hotelService.getHotelById(id);
    }

    /**
     * POST /property-view/hotels — создаём отель и возвращаем краткий DTO.
     */
    @PostMapping("/hotels")
    public HotelShortResponse createHotel(@Valid @RequestBody CreateHotelRequest req) {
        return hotelService.createHotel(req);
    }

    private String formatAddress(Address a) {
        return a.getHouseNumber() + " " + a.getStreet() + ", " +
                a.getCity() + ", " + a.getPostCode() + ", " + a.getCountry();
    }

        /**
     * POST /property-view/hotels/{id}/amenities — добавляем удобства к отелю.
     * По ТЗ тело запроса: массив строк, например ["Free parking","Free WiFi"].
     * Возвращаем обновлённый подробный DTO.
     */
    @PostMapping("/hotels/{id}/amenities")
    public com.example.propertyview.dto.HotelDetailsResponse addAmenities(
            @PathVariable long id,
            @RequestBody java.util.List<String> amenities
    ) {
        return hotelService.addAmenities(id, amenities);
    }
}