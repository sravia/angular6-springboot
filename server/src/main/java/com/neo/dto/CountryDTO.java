package com.neo.dto;

public class CountryDTO {

    private String name;

    private int countryCode;

    public CountryDTO() {
    }

    public CountryDTO(String name, int countryCode) {
        this.name = name;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String toString() {
        return "Country " + name + ". Country code " + countryCode;
    }
}
