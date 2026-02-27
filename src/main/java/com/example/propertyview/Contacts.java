package com.example.propertyview;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Вложенный объект "Контакты".
 */
@Embeddable
public class Contacts {

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    public Contacts() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
