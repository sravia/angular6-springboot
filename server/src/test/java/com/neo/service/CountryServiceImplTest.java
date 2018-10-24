package com.neo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.CountryApplication;
import com.neo.dto.CountryDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CountryApplication.class)
public class CountryServiceImplTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private CountryService countryService;
    private String countriesFromJson;

    @Before
    public void setup() throws URISyntaxException, IOException {
        countriesFromJson = new String(Files.readAllBytes(Paths.get(getClass().getResource("/testCountriesResponse.json").toURI())));
    }

    @Test
    public void testGetCountries() throws JsonProcessingException {
        List<CountryDTO> countries = countryService.getCountries();

        assertEquals(countriesFromJson, MAPPER.writeValueAsString(countries));
    }

    @Test
    public void testGetCountry() throws Exception {
        Optional<CountryDTO> countryDTO = countryService.getCountry(371);

        List<CountryDTO> countries = MAPPER.readValue(countriesFromJson, new TypeReference<List<CountryDTO>>() {
        });
        Optional<CountryDTO> fakeCountryDTO = countries.stream()
                .filter(country -> country.getCountryCode() == 371)
                .findFirst();

        assertTrue(countryDTO.isPresent());
        assertTrue(fakeCountryDTO.isPresent());
        assertEquals(countryDTO.get().getName(), fakeCountryDTO.get().getName());
        assertEquals(countryDTO.get().getCountryCode(), fakeCountryDTO.get().getCountryCode());

    }

}