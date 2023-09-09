package org.example.bd.ui;

import java.math.BigDecimal;

public class Owners {


    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private int phoneNumber;
    private String email;
    private String houseAddress;
    private int houseNumber;
    private int apartmentNumber;
    private float apartmentSqare;

    public int getId() {
        return id;
    }

    public Owners setId(final int id) {
        this.id = id;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Owners setSurname(final String surname) {
        this.surname = surname;
        return this;
    }

    public String getName() {
        return name;
    }

    public Owners setName(final String name) {
        this.name = name;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Owners setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public Owners setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Owners setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public Owners setHouseAddress(final String houseAddress) {
        this.houseAddress = houseAddress;
        return this;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public Owners setHouseNumber(final int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public Owners setApartmentNumber(final int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
        return this;
    }

    public float getApartmentSqare() {
        return apartmentSqare;
    }

    public Owners setApartmentSqare(final float apartmentSqare) {
        this.apartmentSqare = apartmentSqare;
        return this;
    }
}
