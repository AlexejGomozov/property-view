package com.example.propertyview;

import java.time.LocalTime;
import jakarta.persistence.Embeddable;

/**
 * Вложенный объект "Время заезда/выезда".
 * checkOut по ТЗ может быть optional (null).
 */
@Embeddable
public class ArrivalTime {

    private LocalTime checkIn;
    private LocalTime checkOut;

    public ArrivalTime() {}

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }
}