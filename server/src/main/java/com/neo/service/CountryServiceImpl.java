package com.neo.service;

import com.neo.dto.CountryDTO;
import com.neo.util.CountryParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private List<CountryDTO> countries;

    public CountryServiceImpl() {
        CountryParser countryParser = new CountryParser();
        this.countries = countryParser.parseCountries(countryParser.getDocument());
    }

    public Optional<CountryDTO> getCountry(int countryCode) {
        return countries.stream().filter(country -> countryCode == country.getCountryCode()).findFirst();
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }
}