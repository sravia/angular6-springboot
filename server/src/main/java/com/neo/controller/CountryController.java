package com.neo.controller;

import com.neo.dto.CountryDTO;
import com.neo.exception.CountryNotFoundException;
import com.neo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{countryCode}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable("countryCode") int countryCode) {
        return countryService.getCountry(countryCode)
                .map(country -> new ResponseEntity<>(country, HttpStatus.OK))
                .orElseThrow(() -> new CountryNotFoundException(countryCode));
    }

    @GetMapping("")
    public ResponseEntity<List<CountryDTO>> getCountries() {
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }
}
