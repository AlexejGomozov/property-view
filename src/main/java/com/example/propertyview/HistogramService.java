package com.example.propertyview;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Сервис для /histogram/{param}.
 * Возвращает "значение параметра -> количество отелей".
 */
@Service
public class HistogramService {

    private final HotelRepository hotelRepository;

    public HistogramService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Map<String, Long> histogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();

        return switch (param.toLowerCase()) {
            case "brand" -> hotels.stream()
                    .map(Hotel::getBrand)
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

            case "city" -> hotels.stream()
                    .map(h -> h.getAddress().getCity())
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

            case "country" -> hotels.stream()
                    .map(h -> h.getAddress().getCountry())
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

            case "amenities" -> hotels.stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .filter(s -> s != null && !s.isBlank())
                    .map(String::trim)
                    .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported histogram param: " + param + " (use brand/city/country/amenities)"
            );
        };
    }
}
