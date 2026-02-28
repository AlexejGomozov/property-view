package com.example.propertyview;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.propertyview.dto.CreateHotelRequest;
import com.example.propertyview.dto.HotelShortResponse;

/**
 * Сервис — место, где живёт логика.
 * Контроллер вызывает сервис, сервис сохраняет в БД и возвращает DTO.
 */
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Создаём новый отель в базе и возвращаем краткую информацию (как в GET /hotels).
     */
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
            amenities.addAll(req.amenities());
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

    private LocalTime parseTime(String value) {
        return LocalTime.parse(value); // ожидаем формат "HH:mm"
    }

    private String formatAddress(Address a) {
        return a.getHouseNumber() + " " + a.getStreet() + ", " +
                a.getCity() + ", " + a.getPostCode() + ", " + a.getCountry();
    }
}