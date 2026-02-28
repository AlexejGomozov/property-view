package com.example.propertyview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Репозиторий — доступ к таблице hotels.
 * JpaSpecificationExecutor нужен для поиска /search с разными фильтрами.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
}