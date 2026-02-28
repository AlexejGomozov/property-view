package com.example.propertyview;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.propertyview.dto.CreateHotelRequest;
import com.example.propertyview.dto.HotelDetailsResponse;
import com.example.propertyview.dto.HotelShortResponse;

/**
 * Сервис — здесь логика работы с отелями.
 */
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Создаём новый отель в базе и возвращаем краткий DTO (как в GET /hotels).
     */
    @Transactional
    public HotelShortResponse createHotel(CreateHotelRequest req) {
        Hotel hotel = new Hotel();

        hotel.setName(req.name());
        hotel.setDescription(req.description());
        hotel.setBrand(req.brand());

        // address
        Address address = new Address();
        address.setHouseNumber(req.address().houseNumber());
        address.setStreet(req.address().street());
        address.setCity(req.address().city());
        address.setCountry(req.address().country());
        address.setPostCode(req.address().postCode());
        hotel.setAddress(address);

        // contacts
        Contacts contacts = new Contacts();
        contacts.setPhone(req.contacts().phone());
        contacts.setEmail(req.contacts().email());
        hotel.setContacts(contacts);

        // arrivalTime
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(parseTime(req.arrivalTime().checkIn()));
        arrivalTime.setCheckOut(req.arrivalTime().checkOut() == null ? null : parseTime(req.arrivalTime().checkOut()));
        hotel.setArrivalTime(arrivalTime);

        // amenities
        Set<String> amenities = new LinkedHashSet<>();
        if (req.amenities() != null) {
            for (String a : req.amenities()) {
                if (a != null && !a.isBlank()) {
                    amenities.add(a.trim());
                }
            }
        }
        hotel.setAmenities(amenities);

        Hotel saved = hotelRepository.save(hotel);

        return new HotelShortResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                formatAddress(saved.getAddress()),
                saved.getContacts().getPhone()
        );
    }

    /**
     * Получаем один отель по id и возвращаем подробный DTO.
     * Если отеля нет — отдаём 404.
     */
    @Transactional(readOnly = true)
    public HotelDetailsResponse getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Hotel not found: " + id
                ));

        return toDetailsResponse(hotel);
    }

    /**
     * Добавляем удобства (amenities) к отелю.
     * Если отеля нет — 404.
     */
    @Transactional
    public HotelDetailsResponse addAmenities(long id, java.util.List<String> amenitiesToAdd) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Hotel not found: " + id
                ));

        if (amenitiesToAdd != null) {
            for (String a : amenitiesToAdd) {
                if (a != null && !a.isBlank()) {
                    hotel.getAmenities().add(a.trim());
                }
            }
        }

        Hotel saved = hotelRepository.save(hotel);
        return toDetailsResponse(saved);
    }

    private HotelDetailsResponse toDetailsResponse(Hotel hotel) {
        return new HotelDetailsResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                new HotelDetailsResponse.Address(
                        hotel.getAddress().getHouseNumber(),
                        hotel.getAddress().getStreet(),
                        hotel.getAddress().getCity(),
                        hotel.getAddress().getCountry(),
                        hotel.getAddress().getPostCode()
                ),
                new HotelDetailsResponse.Contacts(
                        hotel.getContacts().getPhone(),
                        hotel.getContacts().getEmail()
                ),
                new HotelDetailsResponse.ArrivalTime(
                        hotel.getArrivalTime().getCheckIn().toString(),
                        hotel.getArrivalTime().getCheckOut() == null ? null : hotel.getArrivalTime().getCheckOut().toString()
                ),
                hotel.getAmenities().stream().toList()
        );
    }

    private LocalTime parseTime(String value) {
        return LocalTime.parse(value); // ожидаем формат "HH:mm"
    }

    private String formatAddress(Address a) {
        return a.getHouseNumber() + " " + a.getStreet() + ", " +
                a.getCity() + ", " + a.getPostCode() + ", " + a.getCountry();
    }
}