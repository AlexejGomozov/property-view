package com.example.propertyview;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.propertyview.dto.HotelShortResponse;

/**
 * Контроллер поиска по отелям (по ТЗ: /search).
 */
@RestController
@RequestMapping("/property-view")
public class SearchController {

    private final HotelRepository hotelRepository;

    public SearchController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/search")
    public List<HotelShortResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {
org.springframework.data.jpa.domain.Specification<Hotel> spec =
        org.springframework.data.jpa.domain.Specification.where((root, query, cb) -> cb.conjunction());
        if (name != null && !name.isBlank()) {
            spec = spec.and(HotelSpecifications.nameContains(name.trim()));
        }
        if (brand != null && !brand.isBlank()) {
            spec = spec.and(HotelSpecifications.brandEqualsIgnoreCase(brand.trim()));
        }
        if (city != null && !city.isBlank()) {
            spec = spec.and(HotelSpecifications.cityEqualsIgnoreCase(city.trim()));
        }
        if (country != null && !country.isBlank()) {
            spec = spec.and(HotelSpecifications.countryEqualsIgnoreCase(country.trim()));
        }
        if (amenities != null && !amenities.isBlank()) {
            // В ТЗ amenities приходит строкой. Разрешим варианты:
            // "WiFi" или "WiFi,Pool" (через запятую)
            java.util.List<String> list = java.util.Arrays.stream(amenities.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .toList();
            if (!list.isEmpty()) {
                spec = spec.and(HotelSpecifications.hasAnyAmenities(list));
            }
        }

        return hotelRepository.findAll(spec).stream()
                .map(h -> new HotelShortResponse(
                        h.getId(),
                        h.getName(),
                        h.getDescription(),
                        formatAddress(h.getAddress()),
                        h.getContacts().getPhone()
                ))
                .toList();
    }

    private String formatAddress(Address a) {
        return a.getHouseNumber() + " " + a.getStreet() + ", " +
                a.getCity() + ", " + a.getPostCode() + ", " + a.getCountry();
    }
}