package com.example.propertyview;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий — это "доступ к таблице" через Java-интерфейс.
 * Spring сам создаст реализацию во время запуска.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}