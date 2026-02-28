package com.example.propertyview;

import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

/**
 * Specifications — это “условия поиска”, которые можно комбинировать.
 * Мы будем собирать их в /search в зависимости от query-параметров.
 */
public final class HotelSpecifications {

    private HotelSpecifications() {
    }

    public static Specification<Hotel> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> brandEqualsIgnoreCase(String brand) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Hotel> cityEqualsIgnoreCase(String city) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> countryEqualsIgnoreCase(String country) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    /**
     * Поиск по amenities (коллекция строк). Если передали несколько — ищем “любой из”.
     */
      /**
     * Поиск по amenities (коллекция строк).
     * Ищем “любой из” переданных вариантов.
     */
 /**
 * Поиск по amenities (коллекция строк).
 * Ищем “любой из” переданных вариантов.
 */
public static Specification<Hotel> hasAnyAmenities(List<String> amenities) {
    return (root, query, cb) -> {
        if (amenities == null || amenities.isEmpty()) {
            return cb.conjunction(); // без фильтра
        }

        query.distinct(true);

        var cleaned = amenities.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .toList();

        if (cleaned.isEmpty()) {
            return cb.conjunction();
        }

        // ElementCollection (Set<String>) — надёжно фильтровать через join
        return root.join("amenities").in(cleaned);
    };
}
}
