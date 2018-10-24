package com.neo.service;

import com.neo.dto.CountryDTO;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<CountryDTO> getCountries();

    Optional<CountryDTO> getCountry(int countryCode);
}
