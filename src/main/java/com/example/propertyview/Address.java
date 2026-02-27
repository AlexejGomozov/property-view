package com.example.propertyview;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Вложенный объект "Адрес".
 * @Embeddable означает: поля этого класса будут храниться в таблице hotels как обычные колонки.
 */
@Embeddable
public class Address {

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "post_code", nullable = false)
    private String postCode;

    // Пустой конструктор нужен JPA
    public Address() {}

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}