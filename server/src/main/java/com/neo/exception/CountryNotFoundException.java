package com.neo.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(int countryCode) {
        super("Could not find country with country code: " + countryCode);
    }
}
